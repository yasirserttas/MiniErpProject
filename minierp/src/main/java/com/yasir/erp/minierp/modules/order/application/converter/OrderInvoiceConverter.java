package com.yasir.erp.minierp.modules.order.application.converter;

import com.yasir.erp.minierp.modules.order.application.dto.OrderInvoiceDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderInvoiceConverter {

    public OrderInvoiceDto convertToOrderInvoiceDto(Order order) {
        return new OrderInvoiceDto(
                order.getId(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getTotalAmount(),
                order.getActive()
        );
    }

    public Set<OrderInvoiceDto> convertToSetOrderInvoiceDto(Set<Order> orders) {
        return orders.stream()
                .map(this::convertToOrderInvoiceDto)
                .collect(Collectors.toSet());
    }
}
