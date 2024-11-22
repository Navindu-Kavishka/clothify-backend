package com.nk.clothify_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//checked


@Entity
@Table(name = "order_Item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JsonIgnore
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity productEntity;


    private String size;

    private int quantity;

    private Integer price;

    private Integer discountedPrice;

    private Long userId;

    private LocalDateTime deliveryDate;


}
