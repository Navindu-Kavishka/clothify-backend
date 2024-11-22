package com.nk.clothify_backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//checked

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @ManyToOne
    private CartEntity cartEntity;

    @ManyToOne
    private ProductEntity  productEntity;

    private String size;

    private Integer quantity;

    private Integer price;

    @Column(name = "discounted_price")
    private Integer discountedPrice;

    private Long userId;


}
