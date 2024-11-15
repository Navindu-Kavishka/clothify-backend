package com.nk.clothify_backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Long id;

    private Cart cart;

    private Product product;

    private String size;

    private Integer quantity;

    private Double price;

    private Double discountedPrice;

    private Long userId;

}
