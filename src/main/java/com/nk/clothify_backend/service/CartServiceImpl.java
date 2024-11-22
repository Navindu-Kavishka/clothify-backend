package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.entity.CartItemEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.CartItem;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.CartRepository;
import com.nk.clothify_backend.request.AddItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{


    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final ObjectMapper mapper;
    private final ModelMapper modelMapper;

    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUserEntity(modelMapper.map(user, UserEntity.class));
        return modelMapper.map(
                cartRepository.save(
                        modelMapper.map(cart, CartEntity.class)
                ), Cart.class
        );
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        CartEntity cartEntity = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());


        CartItem isCartItemExist = cartItemService.isCartItemExist(modelMapper.map(cartEntity, Cart.class), product, req.getSize(), userId);
        if (isCartItemExist==null){
            CartItem cartItem = new CartItem();
            cartItem.setProductEntity(modelMapper.map(product, ProductEntity.class));
            cartItem.setCartEntity(cartEntity);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            cartItem.setPrice(req.getQuantity()* product.getDiscountedPrice());
            cartItem.setSize(req.getSize());



            CartItem createdItem = cartItemService.createCartItem(mapper.convertValue(cartItem, CartItem.class));

            cartEntity.getCartItemEntities().add(mapper.convertValue(createdItem, CartItemEntity.class));
        }
        return "Item added to cart...";
    }
    @Override
    public Cart findUserCart(Long userId) {


        CartEntity cartEntity = cartRepository.findByUserId(userId);

        if (cartEntity == null) {
            log.error("Cart not found for user ID: " + userId);
        }


        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;


        for (CartItemEntity cartItemEntity :cartEntity.getCartItemEntities()){
            totalPrice = totalPrice + cartItemEntity.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItemEntity.getDiscountedPrice();
            totalItem = totalItem + cartItemEntity.getQuantity();
        }

        cartEntity.setTotalPrice(totalPrice);
        cartEntity.setTotalDiscountedPrice(totalDiscountedPrice);
        cartEntity.setTotalItem(totalItem);
        cartEntity.setDiscount((totalPrice-totalDiscountedPrice));

        log.info("cart : "+cartEntity);
        log.info("end run findUserCart");

        CartEntity saved = cartRepository.save(cartEntity);

        log.info("saved : "+saved);


        // Convert to Cart DTO
        Cart cart = mapToCart(saved); // Use the explicit mapping method
        log.info("Returning cart: " + cart);

        return cart;

    }

    public Cart mapToCart(CartEntity cartEntity) {
        Cart cart = new Cart();
        cart.setId(cartEntity.getId());
        cart.setUserEntity(cartEntity.getUserEntity());
        cart.setTotalPrice(cartEntity.getTotalPrice());
        cart.setTotalDiscountedPrice(cartEntity.getTotalDiscountedPrice());
        cart.setTotalItem(cartEntity.getTotalItem());
        cart.setDiscount(cartEntity.getDiscount());

        Set<CartItemEntity> cartItemEntities = cartEntity.getCartItemEntities();
        Set<CartItemEntity> cartItems = cartItemEntities != null ? new HashSet<>(cartItemEntities) : new HashSet<>();
        cart.setCartItemEntities(cartItems);

        return cart;
    }
}
