package com.nk.clothify_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Double price;

    private Double discountedPrice;

    private Long userId;

}
