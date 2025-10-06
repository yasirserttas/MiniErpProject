package com.yasir.erp.minierp.modules.purchaseItem.application.service.command;

import com.yasir.erp.minierp.modules.purchaseItem.application.excepiton.PurchaseItemNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.UpdatePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.command.PurchaseItemCommandPort;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.UpdatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseItem.application.converter.PurchaseOrderItemConverter;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdatePurchaseItemService implements UpdatePurchaseItemUseCase {

    private final PurchaseItemQueryPort queryPort;
    private final PurchaseItemCommandPort commandPort;
    private final PurchaseOrderItemConverter converter;

    public UpdatePurchaseItemService(PurchaseItemQueryPort queryPort,
                                     PurchaseItemCommandPort commandPort,
                                     PurchaseOrderItemConverter converter) {
        this.queryPort = queryPort;
        this.commandPort = commandPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PurchaseItemDto updatePurchaseItem(UpdatePurchaseItemDtoRequest updatePurchaseItemDtoRequest) {

        PurchaseItem existing = queryPort.findById(updatePurchaseItemDtoRequest.getId())
                .orElseThrow(() -> new PurchaseItemNotFoundException
                        (updatePurchaseItemDtoRequest.getId()));

        Product product = existing.getProduct();
        BigDecimal purchasePrice = updatePurchaseItemDtoRequest.getPurchasePrice();
        int quantity = updatePurchaseItemDtoRequest.getQuantity();
        BigDecimal taxRate = product.getVatRate();
        BigDecimal taxAmount = purchasePrice.multiply(taxRate);
        BigDecimal totalCost = purchasePrice.add(taxAmount).multiply(BigDecimal.valueOf(quantity));

        PurchaseItem updated = new PurchaseItem(
                existing.getId(),
                product,
                existing.getPurchaseOrder(),
                quantity,
                purchasePrice,
                taxRate,
                taxAmount,
                totalCost
        );

        return converter.convertToPurchaseOrderItemDto(commandPort.save(updated));
    }
}
