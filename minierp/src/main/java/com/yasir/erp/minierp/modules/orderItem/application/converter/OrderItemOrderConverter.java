package com.yasir.erp.minierp.modules.orderItem.application.converter;

import com.yasir.erp.minierp.modules.product.application.converter.ProductOrderItemConverter;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemOrderDto;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderItemOrderConverter {

    private ProductOrderItemConverter productOrderItemConverter;

    public OrderItemOrderConverter(ProductOrderItemConverter productOrderItemConverter) {
        this.productOrderItemConverter = productOrderItemConverter;
    }

    public OrderItemOrderDto convertToOrderItemOrder(OrderItem orderItem){
        return new OrderItemOrderDto(
                orderItem.getId(),
                productOrderItemConverter.convertToProductOrderItemDto(orderItem.getProduct()),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getTaxRateApplied(),
                orderItem.getTaxAmount(),
                orderItem.getTotalPriceWithTax()
        );
    }

    public Set<OrderItemOrderDto> convertToSetOrderItemOrder(Set<OrderItem> orderItems){
        return orderItems.stream().map(this::convertToOrderItemOrder).collect(Collectors.toSet());
    }
}
