package com.nk.clothify_backend.service;

import com.nk.clothify_backend.exception.CartItemException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.CartItem;
import com.nk.clothify_backend.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;



}
