package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.entity.CartItemEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.exception.CartItemException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.CartItem;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.CartItemRepository;
import com.nk.clothify_backend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final ObjectMapper mapper;



    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProductEntity().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProductEntity().getDiscountedPrice()*cartItem.getQuantity());

        CartItemEntity cartItemEntity = mapper.convertValue(cartItem, CartItemEntity.class);

        CartItemEntity createdCartItemEntity=cartItemRepository.save(cartItemEntity);
        CartItem converted = mapper.convertValue(createdCartItemEntity, CartItem.class);


        return converted;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProductEntity().getPrice());
            item.setDiscountedPrice(item.getProductEntity().getDiscountedPrice()*item.getQuantity());
        }
        return mapper.convertValue(
                cartItemRepository.save(
                        mapper.convertValue(item, CartItemEntity.class)
                ), CartItem.class);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartEntity cartEntity = mapper.convertValue(cart, CartEntity.class);
        ProductEntity productEntity = mapper.convertValue(product, ProductEntity.class);

        CartItemEntity cartItemEntityExist = cartItemRepository.isCartItemExist(cartEntity, productEntity, size, userId);
        return mapper.convertValue(cartItemEntityExist, CartItem.class);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItemById = findCartItemById(cartItemId);
        User userById = userService.findUserById(cartItemById.getUserId());
        User reqUser = userService.findUserById(userId);

        if (userById.getId().equals(reqUser.getId())) {
            cartItemRepository.deleteById(cartItemId);
        }
        else {
            throw new UserException("you can't remove another users item");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItemEntity> opt = cartItemRepository.findById(cartItemId);

        if (opt.isPresent()) {
            return mapper.convertValue(opt.get(), CartItem.class);
        }
        throw new CartItemException("cart item not found with ID: "+cartItemId);
    }
}
