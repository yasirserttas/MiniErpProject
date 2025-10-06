package com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;

public interface OrderItemCommandPort {
    OrderItem save(OrderItem item);
    void delete(OrderItem item);
}
