package com.nk.clothify_backend.model;

import com.nk.clothify_backend.entity.OrderEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Long id;
    private OrderEntity orderEntity;
    private ProductEntity productEntity;
    private String size;
    private int quantity;
    private int price;
    private int discountedPrice;
    private Long userId;
    private LocalDateTime deliveryDate;


}
