package com.nk.clothify_backend.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

//checked

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


    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "total_item")
    private Integer totalItem;

    @Column(name = "total_discounted_price")
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
