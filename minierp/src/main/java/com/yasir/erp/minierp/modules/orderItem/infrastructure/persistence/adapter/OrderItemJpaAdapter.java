package com.yasir.erp.minierp.modules.orderItem.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.command.OrderItemCommandPort;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.query.OrderItemQueryPort;
import com.yasir.erp.minierp.modules.orderItem.infrastructure.persistence.OrderItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class OrderItemJpaAdapter implements OrderItemCommandPort, OrderItemQueryPort {

    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItemJpaAdapter(OrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }

    @Override public OrderItem save(OrderItem item) {
        return orderItemJpaRepository.save(item);
    }

    @Override public void delete(OrderItem item) {
        orderItemJpaRepository.delete(item);
    }

    @Override public Optional<OrderItem> findById(String id) {
        return orderItemJpaRepository.findById(id);
    }

    @Override public Set<OrderItem> findAllByOrderId(String orderId) {
        return orderItemJpaRepository.findAllByOrder_Id(orderId);
    }

}
