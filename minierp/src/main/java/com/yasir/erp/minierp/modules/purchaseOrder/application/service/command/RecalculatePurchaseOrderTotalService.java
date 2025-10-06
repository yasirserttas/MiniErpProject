package com.yasir.erp.minierp.modules.purchaseOrder.application.service.command;

import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.RecalculatePurchaseOrderTotalUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command.PurchaseOrderCommandPort;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.PurchaseOrderQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class RecalculatePurchaseOrderTotalService implements RecalculatePurchaseOrderTotalUseCase {

    private final PurchaseOrderCommandPort commandPort;
    private final PurchaseOrderQueryPort queryPort;
    private final PurchaseItemQueryPort purchaseItemQueryPort;

    public RecalculatePurchaseOrderTotalService(PurchaseOrderCommandPort commandPort,
                                                PurchaseOrderQueryPort queryPort,
                                                PurchaseItemQueryPort purchaseItemQueryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.purchaseItemQueryPort = purchaseItemQueryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void recalculateTotalAmount(String orderId) {
        PurchaseOrder existing = queryPort.findByIdAndActive(orderId, true)
                .orElseThrow(()-> new PurchaseOrderNotFoundException(orderId));

        Set<PurchaseItem> items = purchaseItemQueryPort.findAllByPurchaseOrderId(orderId);

        BigDecimal total = items.stream()
                .map(PurchaseItem::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseOrder updated = new PurchaseOrder(
                existing.getId(), existing.getSupplier(), items, total,
                existing.getStatus(), existing.getCreatedAt(), LocalDateTime.now(), existing.getActive()
        );

        commandPort.save(updated);
    }
}
