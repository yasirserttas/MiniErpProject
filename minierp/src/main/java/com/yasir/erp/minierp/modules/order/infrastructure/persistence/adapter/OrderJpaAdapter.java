package com.yasir.erp.minierp.modules.order.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.order.infrastructure.persistence.OrderJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class OrderJpaAdapter implements
        OrderCommandPort,
        OrderQueryPort,
        OrderActiveQueryPort,
        OrderCustomerQueryPort,
        OrderUserQueryPort,
        OrderStatusQueryPort,
        OrderCreatedAtQueryPort,
        OrderUpdatedAtQueryPort,
        OrderDateQueryPort,
        OrderStatusCreatedAtQueryPort,
        OrderStatusOrderDateQueryPort {

    private final OrderJpaRepository repo;

    public OrderJpaAdapter(OrderJpaRepository repo) {
        this.repo = repo;
    }

    @Override public Order save(Order order) {
        return repo.save(order);
    }

    @Override public Optional<Order> findById(String id) {
        return repo.findById(id);
    }

    @Override public Optional<Order> findByIdAndActive(String id, boolean active) {
        return repo.findByIdAndActive(id, active);
    }

    @Override public Optional<Order> findByCustomerIdAndActive(UUID customerId, boolean active) {
        return repo.findByCustomerIdAndActive(customerId, active);
    }

    @Override public Set<Order> findAllByCustomerIdAndActive(UUID customerId, boolean active) {
        return repo.findAllByCustomerIdAndActive(customerId, active);
    }

    @Override public Optional<Order> findByUserIdAndActive(UUID userId, boolean active) {
        return repo.findByUserIdAndActive(userId, active);
    }

    @Override public Set<Order> findAllByUserIdAndActive(UUID userId, boolean active) {
        return repo.findAllByUserIdAndActive(userId, active);
    }

    @Override public Optional<Order> findByOrderStatusAndActive(OrderStatus status, boolean active) {
        return repo.findByOrderStatusAndActive(status, active);
    }

    @Override public Set<Order> findAllByOrderStatusAndActive(OrderStatus status, boolean active) {
        return repo.findAllByOrderStatusAndActive(status, active);
    }

    @Override public Set<Order> findAllByCreatedAtBetweenAndActive
            (LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByCreatedAtBetweenAndActive(s, e, a);
    }

    @Override public Set<Order> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByUpdatedAtBetweenAndActive(s, e, a);
    }

    @Override public Set<Order> findAllByOrderDateBetweenAndActive
            (LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByOrderDateBetweenAndActive(s, e, a);
    }

    @Override public Set<Order> findAllByOrderStatusAndCreatedAtBetweenAndActive
            (OrderStatus st, LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByOrderStatusAndCreatedAtBetweenAndActive(st, s, e, a);
    }

    @Override public Set<Order> findAllByOrderStatusAndOrderDateBetweenAndActive
            (OrderStatus st, LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByOrderStatusAndOrderDateBetweenAndActive(st, s, e, a);
    }
}
