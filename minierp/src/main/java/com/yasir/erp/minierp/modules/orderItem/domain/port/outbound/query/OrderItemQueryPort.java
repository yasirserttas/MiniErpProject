package com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;

import java.util.Optional;
import java.util.Set;

public interface OrderItemQueryPort {
    Optional<OrderItem> findById(String id);
    Set<OrderItem> findAllByOrderId(String orderId);
}
