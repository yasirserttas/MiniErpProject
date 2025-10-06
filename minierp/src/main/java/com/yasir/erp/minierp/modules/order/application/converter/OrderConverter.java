package com.yasir.erp.minierp.modules.order.application.converter;

import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.customer.application.converter.CustomerOrderConverter;
import com.yasir.erp.minierp.modules.orderItem.application.converter.OrderItemOrderConverter;
import com.yasir.erp.minierp.modules.user.application.converter.UserOrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    private final AddressConverter addressConverter;
    private final CustomerOrderConverter customerOrderConverter;
    private final UserOrderConverter userOrderConverter;
    private final OrderItemOrderConverter orderItemOrderConverter;

    public OrderConverter(AddressConverter addressConverter,
                          CustomerOrderConverter customerOrderConverter,
                          UserOrderConverter userOrderConverter,
                          OrderItemOrderConverter orderItemOrderConverter) {
        this.addressConverter = addressConverter;
        this.customerOrderConverter = customerOrderConverter;
        this.userOrderConverter = userOrderConverter;
        this.orderItemOrderConverter = orderItemOrderConverter;
    }

    public OrderDto convertToDto(Order order) {
        return new OrderDto(
                order.getId(),
                customerOrderConverter.convertToCustomerOrderDto(order.getCustomer()),
                userOrderConverter.convertToUserOrderDto(order.getUser()),
                orderItemOrderConverter.convertToSetOrderItemOrder(order.getOrderItems()),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                addressConverter.convertToAddress(order.getBillingAddress()),
                addressConverter.convertToAddress(order.getBillingAddress()),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getNote(),
                order.getActive()

        );
    }

    public Set<OrderDto> convertToSetDto(Set<Order> orders){

        return orders.stream().map(this::convertToDto)
                .collect(Collectors.toSet());

    }


}
