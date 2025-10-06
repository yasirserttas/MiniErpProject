package com.yasir.erp.minierp.modules.purchaseOrder.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.purchaseOrder.application.converter.PurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.ActivatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command.PurchaseOrderCommandPort;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.PurchaseOrderQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivatePurchaseOrderService implements ActivatePurchaseOrderUseCase {

    private final PurchaseOrderCommandPort commandPort;
    private final PurchaseOrderQueryPort queryPort;
    private final PurchaseOrderConverter purchaseOrderConverter;

    public ActivatePurchaseOrderService(PurchaseOrderCommandPort commandPort,
                                        PurchaseOrderQueryPort queryPort,
                                        PurchaseOrderConverter purchaseOrderConverter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.purchaseOrderConverter = purchaseOrderConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PurchaseOrderDto activePurchaseOrder(String orderId) {

        PurchaseOrder existing = queryPort.findByIdAndActive(orderId, false)
                .orElseThrow(()-> new OrderNotFoundException(orderId));

        PurchaseOrder updated = new PurchaseOrder(
                existing.getId(),
                existing.getSupplier(),
                existing.getPurchaseItems(),
                existing.getTotalAmount(),
                existing.getStatus(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true
        );
       return purchaseOrderConverter.
               convertToPurchaseOrderDto(commandPort.save(updated));
    }
}
