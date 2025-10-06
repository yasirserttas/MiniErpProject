package com.yasir.erp.minierp.modules.purchaseItem.application.service.query;

import com.yasir.erp.minierp.modules.purchaseItem.application.excepiton.PurchaseItemNotFoundException;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.query.FindPurchaseItemByIdUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.query.ListPurchaseItemsByPurchaseOrderIdUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query.PurchaseItemQueryPort;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.converter.PurchaseOrderItemConverter;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class PurchaseItemQueryService implements
        FindPurchaseItemByIdUseCase,
        ListPurchaseItemsByPurchaseOrderIdUseCase {

    private final PurchaseItemQueryPort queryPort;
    private final PurchaseOrderItemConverter converter;

    public PurchaseItemQueryService(PurchaseItemQueryPort queryPort,
                                    PurchaseOrderItemConverter converter) {
        this.queryPort = queryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PurchaseItemDto findDtoByPurchaseItemId(String id) {
        return converter.convertToPurchaseOrderItemDto(
                queryPort.findById(id).orElseThrow(() -> new PurchaseItemNotFoundException(id))
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseItemDto> findDtoAllByPurchaseOrderId(String purchaseOrderId) {
        return converter.convertToPurchaseOrderItemDtoSet(
                findAllByPurchaseOrderId(purchaseOrderId)
        );
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseItem> findAllByPurchaseOrderId(String purchaseOrderId) {
        return queryPort.findAllByPurchaseOrderId(purchaseOrderId);
    }
}
