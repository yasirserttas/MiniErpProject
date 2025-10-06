package com.yasir.erp.minierp.modules.purchaseItem.infrastructure.persistence;

import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PurchaseItemJpaRepository extends JpaRepository<PurchaseItem, String> {
    Set<PurchaseItem> findAllByPurchaseOrderId(String purchaseOrderId);
}