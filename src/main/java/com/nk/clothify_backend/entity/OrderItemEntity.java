package com.nk.clothify_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "orderItem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @ManyToOne
    private OrderEntity orderEntity;

    @ManyToOne
    private ProductEntity productEntity;


    private String size;

    private int quantity;

    private Double price;

    private Double discountedPrice;

    private Long userId;

    private LocalDateTime deliveryDate;


}
