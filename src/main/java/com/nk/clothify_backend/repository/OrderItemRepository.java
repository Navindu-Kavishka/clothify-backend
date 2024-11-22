package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {

}
