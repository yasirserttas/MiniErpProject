package com.yasir.erp.minierp.modules.purchaseOrder.application.service.command;

import com.yasir.erp.minierp.modules.purchaseItem.application.excepiton.PurchaseItemNotFoundException;
import com.yasir.erp.minierp.modules.supplier.application.exception.SupplierNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.CreatePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseOrder.application.converter.PurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request.CreatePurchaseOrderDtoRequest;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.CreatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command.PurchaseOrderCommandPort;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByIdAndActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class CreatePurchaseOrderService implements CreatePurchaseOrderUseCase {

    private final PurchaseOrderCommandPort commandPort;
    private final SupplierByIdAndActiveQueryPort supplierByIdAndActiveQueryPort;
    private final CreatePurchaseItemUseCase createItemUseCase;
    private final PurchaseItemQueryPort purchaseItemQueryPort;
    private final PurchaseOrderConverter converter;

    public CreatePurchaseOrderService(PurchaseOrderCommandPort commandPort,
                                      SupplierByIdAndActiveQueryPort supplierByIdAndActiveQueryPort,
                                      CreatePurchaseItemUseCase createItemUseCase,
                                      PurchaseItemQueryPort purchaseItemQueryPort,
                                      PurchaseOrderConverter converter) {
        this.commandPort = commandPort;
        this.supplierByIdAndActiveQueryPort = supplierByIdAndActiveQueryPort;
        this.createItemUseCase = createItemUseCase;
        this.purchaseItemQueryPort = purchaseItemQueryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PurchaseOrderDto addPurchaseOrder(CreatePurchaseOrderDtoRequest req) {
        if ( req.getItems() == null || req.getItems().isEmpty()) {
            throw new IllegalArgumentException("Siparişte en az 1 ürün olmalıdır.");
        }

        Supplier supplier = supplierByIdAndActiveQueryPort
                .findByIdAndActive(req.getSupplierId(), true)
                .orElseThrow(()-> new SupplierNotFoundException(req.getSupplierId()));

        Set<PurchaseItem> items = new HashSet<>();
        for (CreatePurchaseItemDtoRequest it : req.getItems()) {
            PurchaseItemDto created = createItemUseCase.createPurchaseItem(it);

            PurchaseItem add = purchaseItemQueryPort.findById(created.getId()).
                    orElseThrow(()-> new PurchaseItemNotFoundException(created.getId()));

            items.add(add);
        }

        BigDecimal total = items.stream()
                .map(PurchaseItem::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseOrder purchaseOrder = new PurchaseOrder(
                UlidGenerator.generate(),
                supplier,
                items,
                total,
                PurchaseOrderStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );

        return converter.convertToPurchaseOrderDto(commandPort.save(purchaseOrder));
    }
}
