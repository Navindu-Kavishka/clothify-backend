package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.OrderItemEntity;
import com.nk.clothify_backend.model.OrderItem;
import com.nk.clothify_backend.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        OrderItemEntity saved = orderItemRepository.save(modelMapper.map(orderItem, OrderItemEntity.class));
        return modelMapper.map(saved,OrderItem.class);
    }
}
