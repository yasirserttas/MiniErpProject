package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.CancelPendingOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CancelPendingOrderService implements CancelPendingOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final OrderConverter orderConverter;

    public CancelPendingOrderService(OrderActiveQueryPort orderActiveQueryPort,
                                     OrderCommandPort orderCommandPort,
                                     OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto cancelPendingOrder(String orderId) {
        Order order = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (OrderStatus.CANCELLED.equals(order.getOrderStatus()) ||
                !OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new IllegalStateException
                    ("Sadece PENDING durumundaki sipariş iptal edilebilir.");
        }

        Order cancelled = new Order(
                order.getId(), order.getCustomer(), order.getUser(), order.getOrderItems(),
                OrderStatus.CANCELLED, order.getCreatedAt(), LocalDateTime.now(),
                order.getShippingAddress(), order.getBillingAddress(), order.getOrderDate(),
                order.getTotalAmount(), order.getNote(), true
        );

        return orderConverter.convertToDto(orderCommandPort.save(cancelled));
    }
}
