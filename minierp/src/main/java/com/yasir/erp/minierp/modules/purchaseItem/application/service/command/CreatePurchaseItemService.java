package com.yasir.erp.minierp.modules.purchaseItem.application.service.command;

import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.CreatePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.command.PurchaseItemCommandPort;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseItem.application.converter.PurchaseOrderItemConverter;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderNotFoundException;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.PurchaseOrderQueryPort;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CreatePurchaseItemService implements CreatePurchaseItemUseCase {

    private final PurchaseItemCommandPort commandPort;
    private final PurchaseOrderItemConverter converter;
    private final ProductQueryPort productQueryPort;
    private final PurchaseOrderQueryPort purchaseOrderQueryPort;

    public CreatePurchaseItemService(PurchaseItemCommandPort commandPort,
                                     PurchaseOrderItemConverter converter,
                                     ProductQueryPort productQueryPort,
                                     PurchaseOrderQueryPort purchaseOrderQueryPort) {
        this.commandPort = commandPort;
        this.converter = converter;
        this.productQueryPort = productQueryPort;
        this.purchaseOrderQueryPort = purchaseOrderQueryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PurchaseItemDto createPurchaseItem(CreatePurchaseItemDtoRequest createPurchaseItemDtoRequest) {
        Product product = productQueryPort.findByIdAndActive
                        (createPurchaseItemDtoRequest.getProductId(), true)
                .orElseThrow(() -> new ProductNotFoundException
                        (createPurchaseItemDtoRequest.getProductId()));

        PurchaseOrder purchaseOrder = purchaseOrderQueryPort.
                findByIdAndActive(createPurchaseItemDtoRequest.getPurchaseOrderId(), true)
                .orElseThrow(() -> new PurchaseOrderNotFoundException
                        (createPurchaseItemDtoRequest.getPurchaseOrderId()));

        BigDecimal purchasePrice = createPurchaseItemDtoRequest.getPurchasePrice();
        int quantity = createPurchaseItemDtoRequest.getQuantity();
        BigDecimal taxRate = product.getVatRate();
        BigDecimal taxAmount = purchasePrice.multiply(taxRate);
        BigDecimal totalCost = purchasePrice.add(taxAmount).multiply(BigDecimal.valueOf(quantity));

        PurchaseItem item = new PurchaseItem(
                UlidGenerator.generate(),
                product,
                purchaseOrder,
                quantity,
                purchasePrice,
                taxRate,
                taxAmount,
                totalCost
        );

        return converter.convertToPurchaseOrderItemDto(commandPort.save(item));
    }
}
