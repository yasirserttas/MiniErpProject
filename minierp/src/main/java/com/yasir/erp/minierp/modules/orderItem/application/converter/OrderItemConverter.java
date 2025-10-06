package com.yasir.erp.minierp.modules.orderItem.application.converter;

import com.yasir.erp.minierp.modules.order.application.converter.OrderOrderItemConverter;
import com.yasir.erp.minierp.modules.product.application.converter.ProductOrderItemConverter;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderItemConverter {

    private final ProductOrderItemConverter productOrderItemConverter;
    private final OrderOrderItemConverter orderOrderItemConverter;

    public OrderItemConverter(ProductOrderItemConverter productOrderItemConverter,
                              OrderOrderItemConverter orderOrderItemConverter) {
        this.productOrderItemConverter = productOrderItemConverter;
        this.orderOrderItemConverter = orderOrderItemConverter;
    }

    public OrderItemDto convertToOrderItemDto(OrderItem orderItem){

        return new OrderItemDto(
                orderItem.getId(),
                orderOrderItemConverter.convertToOrderOrderItemDto(orderItem.getOrder()),
                productOrderItemConverter.convertToProductOrderItemDto(orderItem.getProduct()),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getTotalPriceWithTax()
        );
    }

    public Set<OrderItemDto> convertToOrderItemDtoSet(Set<OrderItem> orderItems ){

        return orderItems.stream().
                map(this::convertToOrderItemDto)
                .collect(Collectors.toSet());

    }


}
