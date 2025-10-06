package com.yasir.erp.minierp.modules.order.application.converter;

import com.yasir.erp.minierp.modules.order.application.dto.OrderOrderItemDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderOrderItemConverter {

    public OrderOrderItemDto convertToOrderOrderItemDto(Order order){

        return new OrderOrderItemDto(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getActive()
        );
    }

    public Set<OrderOrderItemDto> convertToSetOrderOrderItemDto(Set<Order> orders){

        return orders.stream().map(this::convertToOrderOrderItemDto).collect(Collectors.toSet());

    }
 }
