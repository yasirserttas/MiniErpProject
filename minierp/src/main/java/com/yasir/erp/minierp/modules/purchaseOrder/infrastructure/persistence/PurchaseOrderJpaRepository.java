package com.yasir.erp.minierp.modules.purchaseOrder.infrastructure.persistence;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrder, String> {

    Set<PurchaseOrder> findAllByActive(Boolean active);
    Optional<PurchaseOrder> findByIdAndActive(String id, Boolean active);
    Set<PurchaseOrder> findAllBySupplierId(String supplierId);
    Set<PurchaseOrder> findAllBySupplierIdAndActive(String supplierId, Boolean active);
    Set<PurchaseOrder> findAllByStatus(PurchaseOrderStatus status);
    Set<PurchaseOrder> findAllByStatusAndActive(PurchaseOrderStatus status, Boolean active);
    Set<PurchaseOrder> findAllByStatusAndSupplierIdAndActive(PurchaseOrderStatus status, String supplierId, Boolean active);
    Set<PurchaseOrder> findAllByCreatedAtBetweenAndActive(LocalDateTime startDate, LocalDateTime endDate, Boolean active);
    Set<PurchaseOrder> findAllByUpdatedAtBetweenAndActive(LocalDateTime startDate, LocalDateTime endDate, Boolean active);
    Set<PurchaseOrder> findAllByStatusAndCreatedAtBetweenAndActive(
            PurchaseOrderStatus status, LocalDateTime start, LocalDateTime end, Boolean active);

    @Query("""
       select coalesce(sum(po.totalAmount), 0)
       from PurchaseOrder po
       where po.supplier.id = :supplierId
         and po.active = true
       """)
    BigDecimal sumTotalBySupplier(@Param("supplierId") String supplierId);

    @Query("""
       select coalesce(sum(po.totalAmount), 0)
       from PurchaseOrder po
       where po.supplier.id = :supplierId
         and po.active = true
         and po.status <> :cancelled
       """)
    BigDecimal sumOrderedBySupplier(@Param("supplierId") String supplierId,
                                    @Param("cancelled") PurchaseOrderStatus cancelled);
}