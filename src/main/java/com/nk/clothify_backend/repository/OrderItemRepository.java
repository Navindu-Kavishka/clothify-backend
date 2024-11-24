package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.OrderEntity;
import com.nk.clothify_backend.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
    List<OrderItemEntity> findByOrderEntity(OrderEntity orderEntity);
}
//chenged