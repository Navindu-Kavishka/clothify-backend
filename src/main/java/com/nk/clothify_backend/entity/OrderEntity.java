package com.nk.clothify_backend.entity;

import com.nk.clothify_backend.model.PaymentDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @ManyToOne
    private UserEntity userEntity;
    @OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    @OneToOne
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
