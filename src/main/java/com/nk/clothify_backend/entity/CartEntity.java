package com.nk.clothify_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL , orphanRemoval = true)
    @Column(name = "cart_item")
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_item")
    private Integer totalItem;

    private Double totalDiscountedPrice;

    private Double discount;




}
