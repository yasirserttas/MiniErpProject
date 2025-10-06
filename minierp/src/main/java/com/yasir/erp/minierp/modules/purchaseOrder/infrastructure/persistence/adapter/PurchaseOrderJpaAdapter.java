package com.yasir.erp.minierp.modules.purchaseOrder.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command.PurchaseOrderCommandPort;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.*;

import com.yasir.erp.minierp.modules.purchaseOrder.infrastructure.persistence.PurchaseOrderJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class PurchaseOrderJpaAdapter implements
        PurchaseOrderCommandPort,
        PurchaseOrderQueryPort,
        PurchaseOrderActiveQueryPort,
        PurchaseOrderSupplierQueryPort,
        PurchaseOrderSupplierActiveQueryPort,
        PurchaseOrderStatusQueryPort,
        PurchaseOrderStatusActiveQueryPort,
        PurchaseOrderStatusSupplierActiveQueryPort,
        PurchaseOrderUpdatedAtQueryPort,
        PurchaseOrderStatusCreatedAtQueryPort,
        PurchaseOrderAggregationQueryPort {

    private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;

    public PurchaseOrderJpaAdapter(PurchaseOrderJpaRepository purchaseOrderJpaRepository) {
        this.purchaseOrderJpaRepository = purchaseOrderJpaRepository;
    }

    @Override
    public PurchaseOrder save(PurchaseOrder po) {
        return purchaseOrderJpaRepository.save(po);
    }

    @Override
    public Optional<PurchaseOrder> findByIdAndActive(String id, Boolean active) {
        return purchaseOrderJpaRepository.findByIdAndActive(id, active);
    }

    @Override
    public Set<PurchaseOrder> findAllByActive(Boolean active) {
        return purchaseOrderJpaRepository.findAllByActive(active);
    }

    @Override
    public Set<PurchaseOrder> findAllBySupplierId(String supplierId) {
        return purchaseOrderJpaRepository.findAllBySupplierId(supplierId);
    }

    @Override
    public Set<PurchaseOrder> findAllBySupplierIdAndActive(String supplierId, Boolean active) {
        return purchaseOrderJpaRepository.findAllBySupplierIdAndActive(supplierId, active);
    }

    @Override
    public Set<PurchaseOrder> findAllByStatus(PurchaseOrderStatus status) {
        return purchaseOrderJpaRepository.findAllByStatus(status);
    }

    @Override
    public Set<PurchaseOrder> findAllByStatusAndActive(PurchaseOrderStatus status, Boolean active) {
        return purchaseOrderJpaRepository.findAllByStatusAndActive(status, active);
    }

    @Override
    public Set<PurchaseOrder> findAllByStatusAndSupplierIdAndActive(
            PurchaseOrderStatus status, String supplierId, Boolean active) {
        return purchaseOrderJpaRepository.
                findAllByStatusAndSupplierIdAndActive(status, supplierId, active);
    }

    @Override
    public Set<PurchaseOrder> findAllByUpdatedAtBetweenAndActive(
            LocalDateTime start, LocalDateTime end, Boolean active) {
        return purchaseOrderJpaRepository.findAllByUpdatedAtBetweenAndActive(start, end, active);
    }

    @Override
    public Set<PurchaseOrder> findAllByStatusAndCreatedAtBetweenAndActive(
            PurchaseOrderStatus status, LocalDateTime start, LocalDateTime end, Boolean active) {
        return purchaseOrderJpaRepository.
                findAllByStatusAndCreatedAtBetweenAndActive(status, start, end, active);
    }

    @Override
    public BigDecimal sumTotalBySupplier(String supplierId) {
        return purchaseOrderJpaRepository.sumTotalBySupplier(supplierId);
    }
}