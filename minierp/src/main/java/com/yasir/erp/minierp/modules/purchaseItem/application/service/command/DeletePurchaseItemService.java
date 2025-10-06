package com.yasir.erp.minierp.modules.purchaseItem.application.service.command;

import com.yasir.erp.minierp.modules.purchaseItem.application.excepiton.PurchaseItemNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.DeletePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.command.PurchaseItemCommandPort;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderAlreadyFinalizedException;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.RecalculatePurchaseOrderTotalUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePurchaseItemService implements DeletePurchaseItemUseCase {

    private final PurchaseItemQueryPort queryPort;
    private final PurchaseItemCommandPort commandPort;
    private final RecalculatePurchaseOrderTotalUseCase recalculatePurchaseOrderTotalUseCase;

    public DeletePurchaseItemService(PurchaseItemQueryPort queryPort,
                                     PurchaseItemCommandPort commandPort,
                                     RecalculatePurchaseOrderTotalUseCase recalculatePurchaseOrderTotalUseCase) {
        this.queryPort = queryPort;
        this.commandPort = commandPort;
        this.recalculatePurchaseOrderTotalUseCase = recalculatePurchaseOrderTotalUseCase;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(String purchaseItemId) {

        PurchaseItem item = queryPort.findById(purchaseItemId)
                .orElseThrow(() -> new PurchaseItemNotFoundException(purchaseItemId));

        PurchaseOrder purchaseOrder = item.getPurchaseOrder();
        if (!purchaseOrder.getStatus().equals(PurchaseOrderStatus.PENDING)) {
            throw new PurchaseOrderAlreadyFinalizedException(purchaseOrder.getId());
        }

        commandPort.delete(item);
        recalculatePurchaseOrderTotalUseCase.recalculateTotalAmount(purchaseOrder.getId());
    }
}
