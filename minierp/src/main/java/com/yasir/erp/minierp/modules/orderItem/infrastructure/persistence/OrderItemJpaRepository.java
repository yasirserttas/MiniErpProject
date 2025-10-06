package com.yasir.erp.minierp.modules.orderItem.infrastructure.persistence;

import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItem, String> {
    Set<OrderItem> findAllByOrder_Id(String orderId);
}
