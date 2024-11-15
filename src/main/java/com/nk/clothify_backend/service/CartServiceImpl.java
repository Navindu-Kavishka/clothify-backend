package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.entity.CartItemEntity;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.CartItem;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.CartRepository;
import com.nk.clothify_backend.request.AddItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{


    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final ObjectMapper mapper;

    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUserEntity(mapper.convertValue(user, UserEntity.class));
        return mapper.convertValue(
                cartRepository.save(
                        mapper.convertValue(cart, CartEntity.class)
                ), Cart.class
        );
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = mapper.convertValue(cartRepository.findByUserId(userId), Cart.class);
        Product product = productService.findProductById(req.getProductId());

        CartItem isCartItemExist = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if (isCartItemExist==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            cartItem.setPrice(req.getQuantity()* product.getDiscountedPrice());
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItemEntities().add(mapper.convertValue(createdCartItem, CartItemEntity.class));
        }
        return "Item added to cart...";
    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart = mapper.convertValue(cartRepository.findByUserId(userId), Cart.class);

        double totalPrice=0;
        double totalDiscountedPrice=0;
        int totalItem=0;

        for (CartItemEntity cartItemEntity :cart.getCartItemEntities()){
            totalPrice = totalPrice + cartItemEntity.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItemEntity.getDiscountedPrice();
            totalItem = totalItem + cartItemEntity.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return mapper.convertValue(
                cartRepository.save(
                        mapper.convertValue(cart, CartEntity.class)), Cart.class
        );
    }
}
