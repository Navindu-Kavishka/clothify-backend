package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.*;
import com.nk.clothify_backend.exception.OrderException;
import com.nk.clothify_backend.model.*;
import com.nk.clothify_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;
    private final ObjectMapper mapper;
    private final ModelMapper modelMapper;



    @Override
    public Order createOrder(User user, Address shippingAddress) {

        shippingAddress.setUserEntity(userService.mapUserToUserEntity(user));
        AddressEntity saved = addressRepository.save(mapper.convertValue(shippingAddress, AddressEntity.class));
        user.getAddressEntities().add(saved);
        UserEntity userEntity = mapper.convertValue(user, UserEntity.class);
        userRepository.save(userEntity);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItemEntity> orderItemsEntities = new ArrayList<>();

        for (CartItemEntity entity:cart.getCartItemEntities()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(entity.getPrice());
            orderItem.setProductEntity(entity.getProductEntity());
            orderItem.setQuantity(entity.getQuantity());
            orderItem.setSize(entity.getSize());
            orderItem.setUserId(entity.getUserId());
            orderItem.setDiscountedPrice(entity.getDiscountedPrice());

            OrderItemEntity savedEntity = orderItemRepository.save(mapper.convertValue(orderItem, OrderItemEntity.class));
            orderItemsEntities.add(savedEntity);
        }

        Order createdOrder = new Order();
        createdOrder.setUserEntity(userEntity);
        createdOrder.setOrderItemEntities(orderItemsEntities);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddressEntity(saved);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(mapper.convertValue(createdOrder, OrderEntity.class));

        for (OrderItemEntity entity:orderItemsEntities) {
            entity.setOrderEntity(savedOrder);
            orderItemRepository.save(entity);
        }

        return mapper.convertValue(savedOrder, Order.class);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<OrderEntity> byId = orderRepository.findById(orderId);

        if (byId.isPresent()){
            return mapper.convertValue(byId.get(), Order.class);
        }
        throw new OrderException("order not exist with ID : "+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<OrderEntity> usersOrders = orderRepository.getUsersOrders(userId);
        List<Order> orders = new ArrayList<>();
        usersOrders.forEach(orderEntity -> orders.add(mapper.convertValue(orderEntity,Order.class)));
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
        return mapper.convertValue(
                orderRepository.save(
                        mapper.convertValue(order, OrderEntity.class)
                ), Order.class);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return mapper.convertValue(
                orderRepository.save(
                        mapper.convertValue(order,OrderEntity.class)
                ), Order.class);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return mapper.convertValue(
                orderRepository.save(
                        mapper.convertValue(order,OrderEntity.class)
                ), Order.class);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return mapper.convertValue(
                orderRepository.save(
                        mapper.convertValue(order,OrderEntity.class)
                ), Order.class);
    }

    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> all = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        all.forEach(orderEntity -> orders.add(mapper.convertValue(orderEntity, Order.class)));
        return orders;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        findOrderById(orderId);

        orderRepository.deleteById(orderId);
    }

    public OrderItemEntity mapToOrderItemEntity(OrderItem orderItem) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(orderItem.getId());
        entity.setPrice(orderItem.getPrice());
        entity.setOrderEntity(orderItem.getOrderEntity());
        entity.setProductEntity(orderItem.getProductEntity());
        entity.setQuantity(orderItem.getQuantity());
        entity.setSize(orderItem.getSize());
        entity.setUserId(orderItem.getUserId());
        entity.setDiscountedPrice(orderItem.getDiscountedPrice());
        entity.setDeliveryDate(orderItem.getDeliveryDate());
        return entity;
    }

    public OrderEntity mapToOrderEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setOrderId(order.getOrderId());
        entity.setUserEntity(order.getUserEntity());
        entity.setOrderDate(order.getOrderDate());
        entity.setDeliveryDate(order.getDeliveryDate());
        entity.setShippingAddressEntity(order.getShippingAddressEntity());
        entity.setPaymentDetails(order.getPaymentDetails());
        entity.setTotalPrice(order.getTotalPrice());
        entity.setTotalDiscountedPrice(order.getTotalDiscountedPrice());
        entity.setDiscount(order.getDiscount());
        entity.setOrderStatus(order.getOrderStatus());
        entity.setTotalItem(order.getTotalItem());
        entity.setCreatedAt(order.getCreatedAt());

        List<OrderItemEntity> orderItemEntities = order.getOrderItemEntities();
        List<OrderItemEntity> orderItems = orderItemEntities != null ? new ArrayList<>(orderItemEntities) : new ArrayList<>();
        entity.setOrderItemEntities(orderItems);

        return entity;
    }



}
