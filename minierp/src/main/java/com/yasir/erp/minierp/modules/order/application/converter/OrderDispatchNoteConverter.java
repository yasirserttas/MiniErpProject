package com.yasir.erp.minierp.modules.order.application.converter;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDispatchNoteDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderDispatchNoteConverter {

    public OrderDispatchNoteDto convertToOrderDispatchNoteDto(Order order) {
        return new OrderDispatchNoteDto(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getActive()
        );
    }

    public Set<OrderDispatchNoteDto> convertToSetOrderDispatchNoteDto(Set<Order> orders) {
        return orders.stream()
                .map(this::convertToOrderDispatchNoteDto)
                .collect(Collectors.toSet());
    }
}

