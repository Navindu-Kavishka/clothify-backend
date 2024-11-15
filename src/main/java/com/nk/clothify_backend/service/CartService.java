package com.nk.clothify_backend.service;

import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}
