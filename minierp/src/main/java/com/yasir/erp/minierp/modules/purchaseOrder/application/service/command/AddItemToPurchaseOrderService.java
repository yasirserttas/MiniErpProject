package com.yasir.erp.minierp.modules.purchaseOrder.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.application.excepiton.PurchaseItemNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.CreatePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseOrder.application.converter.PurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.AddItemToPurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command.PurchaseOrderCommandPort;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.PurchaseOrderQueryPort;
import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderAlreadyFinalizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AddItemToPurchaseOrderService implements AddItemToPurchaseOrderUseCase {

    private final PurchaseOrderCommandPort commandPort;
    private final PurchaseOrderQueryPort queryPort;
    private final CreatePurchaseItemUseCase createItemUseCase;
    private final PurchaseItemQueryPort purchaseItemQueryPort;
    private final PurchaseOrderConverter converter;

    public AddItemToPurchaseOrderService(PurchaseOrderCommandPort commandPort,
                                         PurchaseOrderQueryPort queryPort,
                                         CreatePurchaseItemUseCase createItemUseCase,
                                         PurchaseItemQueryPort purchaseItemQueryPort,
                                         PurchaseOrderConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.createItemUseCase = createItemUseCase;
        this.purchaseItemQueryPort = purchaseItemQueryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PurchaseOrderDto addItemToPurchaseOrder(String orderId, CreatePurchaseItemDtoRequest req) {

        PurchaseOrder existing = queryPort.findByIdAndActive(orderId, true)
                .orElseThrow(()->new OrderNotFoundException(orderId));

        if (!existing.getStatus().equals(PurchaseOrderStatus.PENDING)) {
            throw new PurchaseOrderAlreadyFinalizedException(orderId);
        }


        CreatePurchaseItemDtoRequest fixedReq = new CreatePurchaseItemDtoRequest(
                req.getProductId(), orderId, req.getQuantity(),
                req.getPurchasePrice(), req.getTaxRate()
        );

        PurchaseItemDto created = createItemUseCase.createPurchaseItem(fixedReq);

        PurchaseItem newItem = purchaseItemQueryPort.findById(created.getId())
                .orElseThrow(()-> new PurchaseItemNotFoundException(created.getId()));

        Set<PurchaseItem> updatedItems = new HashSet<>(existing.getPurchaseItems());
        updatedItems.add(newItem);

        BigDecimal total = updatedItems.stream()
                .map(PurchaseItem::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseOrder updated = new PurchaseOrder(
                existing.getId(), existing.getSupplier(), updatedItems, total,
                existing.getStatus(), existing.getCreatedAt(), LocalDateTime.now(), existing.getActive()
        );

        return converter.convertToPurchaseOrderDto(commandPort.save(updated));
    }
}
