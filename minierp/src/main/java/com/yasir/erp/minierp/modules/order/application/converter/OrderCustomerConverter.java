package com.yasir.erp.minierp.modules.order.application.converter;

import com.yasir.erp.minierp.modules.order.application.dto.OrderCustomerDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderCustomerConverter {

    public OrderCustomerDto convertToOrderCustomerDto(Order order) {
        return new OrderCustomerDto(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getActive()
        );
    }

    public Set<OrderCustomerDto> convertToSetOrderCustomerDto(Set<Order> orders) {
        return orders.stream()
                .map(this::convertToOrderCustomerDto)
                .collect(Collectors.toSet());
    }
}
