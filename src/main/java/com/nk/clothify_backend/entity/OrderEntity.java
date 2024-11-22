package com.nk.clothify_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nk.clothify_backend.model.PaymentDetails;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference    //prevent circular references during serialization
    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private AddressEntity shippingAddressEntity;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();


    private double totalPrice;

    private Integer totalDiscountedPrice;

    private Integer discount;

    private String orderStatus;

    private int totalItem;

    private LocalDateTime createdAt;

    // Helper methods
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItem.setOrderEntity(this);
        this.orderItemEntities.add(orderItem);
    }

    public void removeOrderItem(OrderItemEntity orderItem) {
        orderItem.setOrderEntity(null);
        this.orderItemEntities.remove(orderItem);
    }

}
