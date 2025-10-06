package com.yasir.erp.minierp.modules.purchaseOrder.application.service.command;

import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderNotFoundException;
import com.yasir.erp.minierp.modules.purchaseOrder.application.converter.PurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request.UpdatePurchaseOrderDtoRequest;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.UpdatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command.PurchaseOrderCommandPort;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.PurchaseOrderQueryPort;
import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderAlreadyFinalizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UpdatePurchaseOrderService implements UpdatePurchaseOrderUseCase {

    private final PurchaseOrderCommandPort commandPort;
    private final PurchaseOrderQueryPort queryPort;
    private final PurchaseOrderConverter converter;

    public UpdatePurchaseOrderService(PurchaseOrderCommandPort commandPort,
                                      PurchaseOrderQueryPort queryPort,
                                      PurchaseOrderConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PurchaseOrderDto updatePurchaseOrder
            (UpdatePurchaseOrderDtoRequest updatePurchaseOrderDtoRequest) {
        PurchaseOrder existing = queryPort.
                findByIdAndActive(updatePurchaseOrderDtoRequest.getId(), true)
                .orElseThrow(()-> new
                        PurchaseOrderNotFoundException(updatePurchaseOrderDtoRequest.getId()));

        if (!existing.getStatus().equals(PurchaseOrderStatus.PENDING)) {
            throw new PurchaseOrderAlreadyFinalizedException(existing.getId());
        }

        BigDecimal total = existing.getPurchaseItems().stream()
                .map(i -> i.getTotalCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseOrder updated = new PurchaseOrder(
                existing.getId(), existing.getSupplier(), existing.getPurchaseItems(), total,
                updatePurchaseOrderDtoRequest.getStatus(),
                existing.getCreatedAt(), LocalDateTime.now(), true
        );

        return converter.convertToPurchaseOrderDto(commandPort.save(updated));
    }
}
