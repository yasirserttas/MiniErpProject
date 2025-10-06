package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.RecalculateTotalAmountUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.query.OrderItemQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class RecalculateTotalAmountService implements RecalculateTotalAmountUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final OrderItemQueryPort orderItemQueryPort;

    public RecalculateTotalAmountService(OrderActiveQueryPort orderActiveQueryPort,
                                         OrderCommandPort orderCommandPort,
                                         OrderItemQueryPort orderItemQueryPort) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.orderItemQueryPort = orderItemQueryPort;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void recalculateTotalAmount(String orderId) {
        Order existing = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        Set<OrderItem> items = orderItemQueryPort.findAllByOrderId(orderId);
        BigDecimal total = items.stream()
                .map(OrderItem::getTotalPriceWithTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order updated = new Order(
                existing.getId(), existing.getCustomer(), existing.getUser(),
                items, existing.getOrderStatus(), existing.getCreatedAt(),
                LocalDateTime.now(), existing.getShippingAddress(), existing.getBillingAddress(),
                existing.getOrderDate(), total, existing.getNote(), true
        );
        orderCommandPort.save(updated);
    }
}
