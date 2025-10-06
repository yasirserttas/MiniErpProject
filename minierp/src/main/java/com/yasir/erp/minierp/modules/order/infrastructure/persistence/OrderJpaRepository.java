package com.yasir.erp.minierp.modules.order.infrastructure.persistence;

import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, String> {

    Set<Order> findAllByCustomerIdAndActive(UUID customerId, boolean active);
    Set<Order> findAllByUserIdAndActive(UUID userId, boolean active);
    Set<Order> findAllByOrderStatusAndActive(OrderStatus status, boolean active);
    Set<Order> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Set<Order> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Set<Order> findAllByOrderDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Set<Order> findAllByOrderStatusAndCreatedAtBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active);
    Set<Order> findAllByOrderStatusAndOrderDateBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active);

    Optional<Order> findByIdAndActive(String id, boolean active);
    Optional<Order> findByCustomerIdAndActive(UUID customerId, boolean active);
    Optional<Order> findByUserIdAndActive(UUID userId, boolean active);
    Optional<Order> findByOrderStatusAndActive(OrderStatus status, boolean active);
}
