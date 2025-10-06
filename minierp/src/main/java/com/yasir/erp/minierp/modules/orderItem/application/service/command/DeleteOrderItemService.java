package com.yasir.erp.minierp.modules.orderItem.application.service.command;

import com.yasir.erp.minierp.modules.orderItem.application.exception.OrderItemNotFoundException;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.RecalculateTotalAmountUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command.DeleteOrderItemUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.command.OrderItemCommandPort;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.query.OrderItemQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteOrderItemService implements DeleteOrderItemUseCase {

    private final OrderItemQueryPort orderItemQueryPort;
    private final OrderItemCommandPort orderItemCommandPort;
    private final RecalculateTotalAmountUseCase recalculateTotalAmount;


    public DeleteOrderItemService(OrderItemQueryPort orderItemQueryPort,
                                  OrderItemCommandPort orderItemCommandPort,
                                  RecalculateTotalAmountUseCase recalculateTotalAmount) {
        this.orderItemQueryPort = orderItemQueryPort;
        this.orderItemCommandPort = orderItemCommandPort;
        this.recalculateTotalAmount = recalculateTotalAmount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrderItem(String orderItemId) {
        OrderItem item = orderItemQueryPort.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found: " + orderItemId));
        String orderId = item.getOrder().getId();
        orderItemCommandPort.delete(item);
        recalculateTotalAmount.recalculateTotalAmount(orderId);
    }
}
