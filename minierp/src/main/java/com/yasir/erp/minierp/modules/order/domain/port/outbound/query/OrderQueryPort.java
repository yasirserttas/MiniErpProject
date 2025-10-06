package com.yasir.erp.minierp.modules.order.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.order.domain.model.Order;

import java.util.Optional;

public interface OrderQueryPort {
    Optional<Order> findById(String id);
}
