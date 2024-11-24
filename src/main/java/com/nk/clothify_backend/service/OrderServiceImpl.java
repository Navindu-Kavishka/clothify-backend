package com.nk.clothify_backend.service;

import com.nk.clothify_backend.entity.AddressEntity;
import com.nk.clothify_backend.entity.OrderEntity;
import com.nk.clothify_backend.entity.OrderItemEntity;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.OrderException;
import com.nk.clothify_backend.model.Address;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.Order;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.AddressRepository;
import com.nk.clothify_backend.repository.OrderItemRepository;
import com.nk.clothify_backend.repository.OrderRepository;
import com.nk.clothify_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;



    @Override
    @Transactional
    public Order createOrder(User user, Address shippingAddress) {
        // Save shipping address
        shippingAddress.setUserEntity(userService.mapUserToUserEntity(user));
        AddressEntity savedAddress = addressRepository.save(modelMapper.map(shippingAddress, AddressEntity.class));

        // Update user with new address
        UserEntity userEntity = userService.mapUserToUserEntity(user);
        userEntity.getAddressEntities().add(savedAddress);
        userEntity = userRepository.save(userEntity);

        // Get cart and create order items
        Cart cart = cartService.findUserCart(user.getId());
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserEntity(userEntity);
        orderEntity.setShippingAddressEntity(savedAddress);
        orderEntity.setTotalPrice(cart.getTotalPrice());
        orderEntity.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        orderEntity.setDiscount(cart.getDiscount());
        orderEntity.setTotalItem(cart.getTotalItem());
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setOrderStatus("PENDING");
        orderEntity.getPaymentDetails().setStatus("PENDING");
        orderEntity.setCreatedAt(LocalDateTime.now());

        // Save order first to get ID
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Create and map order items with proper order reference
        List<OrderItemEntity> orderItems = cart.getCartItemEntities().stream()
                .map(cartItem -> {
                    OrderItemEntity orderItem = new OrderItemEntity();
                    orderItem.setOrderEntity(savedOrder);
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setProductEntity(cartItem.getProductEntity());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setSize(cartItem.getSize());
                    orderItem.setUserId(cartItem.getUserId());
                    orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        // Save all order items in one batch
        orderItems = orderItemRepository.saveAll(orderItems);
        savedOrder.setOrderItemEntities(orderItems);

        // Clear cart after successful order creation
        cartService.clearCart(cart.getId());

        return modelMapper.map(savedOrder, Order.class);
    }


    @Override
    @Transactional
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<OrderEntity> byId = orderRepository.findById(orderId);

        if (byId.isPresent()) {
            OrderEntity orderEntity = byId.get();

            // Force initialization of collections and related entities
            List<OrderItemEntity> items = orderItemRepository.findByOrderEntity(orderEntity);
            items.forEach(item -> Hibernate.initialize(item.getProductEntity()));

            Order order = modelMapper.map(orderEntity, Order.class);
            order.setOrderItemEntities(items);

            return order;
        }
        throw new OrderException("order not exist with ID : "+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<OrderEntity> usersOrders = orderRepository.getUsersOrders(userId);
        List<Order> orders = new ArrayList<>();
        usersOrders.forEach(orderEntity -> orders.add(modelMapper.map(orderEntity,Order.class)));
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return modelMapper.map(
                orderRepository.save(
                        modelMapper.map(order, OrderEntity.class)
                ), Order.class);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return modelMapper.map(
                orderRepository.save(
                        modelMapper.map(order,OrderEntity.class)
                ), Order.class);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return modelMapper.map(
                orderRepository.save(
                        modelMapper.map(order,OrderEntity.class)
                ), Order.class);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return modelMapper.map(
                orderRepository.save(
                        modelMapper.map(order,OrderEntity.class)
                ), Order.class);
    }

    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> all = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        all.forEach(orderEntity -> orders.add(modelMapper.map(orderEntity, Order.class)));
        return orders;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        findOrderById(orderId);

        orderRepository.deleteById(orderId);
    }




}
