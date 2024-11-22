package com.nk.clothify_backend.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userEntity;


    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference // Prevents infinite recursion
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();


    private Integer totalPrice;

    private Integer totalItem;

    private Integer totalDiscountedPrice;

    private Integer discount;


    // Helper methods
    public void addCartItem(CartItemEntity cartItem) {
        cartItem.setCartEntity(this);  // Set the reference in CartItemEntity
        this.cartItemEntities.add(cartItem);
    }

    public void removeCartItem(CartItemEntity cartItem) {
        cartItem.setCartEntity(null);  // Remove the reference in CartItemEntity
        this.cartItemEntities.remove(cartItem);
    }

}
