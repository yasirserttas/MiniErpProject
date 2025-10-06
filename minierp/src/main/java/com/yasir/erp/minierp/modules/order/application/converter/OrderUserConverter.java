package com.yasir.erp.minierp.modules.order.application.converter;

import com.yasir.erp.minierp.modules.order.application.dto.OrderUserDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderUserConverter {

    public OrderUserDto convertToOrderUserDto(Order order) {
        return new OrderUserDto(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getActive()
        );
    }

    public Set<OrderUserDto> convertToSetOrderUserDto(Set<Order> orders) {
        return orders.stream()
                .map(this::convertToOrderUserDto)
                .collect(Collectors.toSet());
    }
}
