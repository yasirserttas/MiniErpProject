package com.yasir.erp.minierp.modules.purchaseItem.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.command.PurchaseItemCommandPort;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseItem.infrastructure.persistence.PurchaseItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class PurchaseItemJpaAdapter implements PurchaseItemCommandPort, PurchaseItemQueryPort {

    private final PurchaseItemJpaRepository purchaseItemJpaRepository;

    public PurchaseItemJpaAdapter(PurchaseItemJpaRepository purchaseItemJpaRepository) {
        this.purchaseItemJpaRepository = purchaseItemJpaRepository;
    }

    @Override
    public PurchaseItem save(PurchaseItem purchaseItem) {
        return purchaseItemJpaRepository.save(purchaseItem);
    }

    @Override
    public void delete(PurchaseItem purchaseItem) {
        purchaseItemJpaRepository.delete(purchaseItem);
    }

    @Override
    public Optional<PurchaseItem> findById(String purchaseItemId) {
        return purchaseItemJpaRepository.findById(purchaseItemId);
    }

    @Override
    public Set<PurchaseItem> findAllByPurchaseOrderId(String purchaseOrderId) {
        return purchaseItemJpaRepository.findAllByPurchaseOrderId(purchaseOrderId);
    }
}