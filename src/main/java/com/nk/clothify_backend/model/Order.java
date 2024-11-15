package com.nk.clothify_backend.model;

import com.nk.clothify_backend.entity.AddressEntity;
import com.nk.clothify_backend.entity.OrderItemEntity;
import com.nk.clothify_backend.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {


    private Long id;

    private String orderId;

    private UserEntity userEntity;

    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;


    private AddressEntity shippingAddressEntity;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();


    private Double totalPrice;

    private Double totalDiscountedPrice;

    private Integer discount;

    private String orderStatus;

    private int totalItem;

    private LocalDateTime createdAt;

}
