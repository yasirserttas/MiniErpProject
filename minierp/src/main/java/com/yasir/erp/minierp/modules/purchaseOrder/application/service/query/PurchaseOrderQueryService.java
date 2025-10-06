package com.yasir.erp.minierp.modules.purchaseOrder.application.service.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.converter.PurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.purchaseOrder.application.exception.PurchaseOrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class PurchaseOrderQueryService implements
        FindPurchaseOrderByIdUseCase,
        GetTotalOrderedBySupplierUseCase,
        ListPurchaseOrdersByActiveUseCase,
        ListPurchaseOrdersByStatusUseCase,
        ListPurchaseOrdersByStatusAndActiveUseCase,
        ListPurchaseOrdersBySupplierUseCase,
        ListPurchaseOrdersBySupplierAndActiveUseCase,
        ListPurchaseOrdersByUpdatedAtBetweenAndActiveUseCase,
        ListPurchaseOrdersByStatusAndCreatedAtBetweenAndActiveUseCase,
        ListPurchaseOrdersByStatusSupplierAndActiveUseCase {

    private final PurchaseOrderConverter converter;
    private final PurchaseOrderQueryPort baseQueryPort;
    private final PurchaseOrderActiveQueryPort activeQueryPort;
    private final PurchaseOrderSupplierQueryPort supplierQueryPort;
    private final PurchaseOrderSupplierActiveQueryPort supplierActiveQueryPort;
    private final PurchaseOrderStatusQueryPort statusQueryPort;
    private final PurchaseOrderStatusActiveQueryPort statusActiveQueryPort;
    private final PurchaseOrderStatusSupplierActiveQueryPort statusSupplierActiveQueryPort;
    private final PurchaseOrderUpdatedAtQueryPort updatedAtQueryPort;
    private final PurchaseOrderStatusCreatedAtQueryPort statusCreatedAtQueryPort;
    private final PurchaseOrderAggregationQueryPort aggregationQueryPort;

    public PurchaseOrderQueryService(PurchaseOrderConverter converter,
                                     PurchaseOrderQueryPort baseQueryPort,
                                     PurchaseOrderActiveQueryPort activeQueryPort,
                                     PurchaseOrderSupplierQueryPort supplierQueryPort,
                                     PurchaseOrderSupplierActiveQueryPort supplierActiveQueryPort,
                                     PurchaseOrderStatusQueryPort statusQueryPort,
                                     PurchaseOrderStatusActiveQueryPort statusActiveQueryPort,
                                     PurchaseOrderStatusSupplierActiveQueryPort statusSupplierActiveQueryPort,
                                     PurchaseOrderUpdatedAtQueryPort updatedAtQueryPort,
                                     PurchaseOrderStatusCreatedAtQueryPort statusCreatedAtQueryPort,
                                     PurchaseOrderAggregationQueryPort aggregationQueryPort) {
        this.converter = converter;
        this.baseQueryPort = baseQueryPort;
        this.activeQueryPort = activeQueryPort;
        this.supplierQueryPort = supplierQueryPort;
        this.supplierActiveQueryPort = supplierActiveQueryPort;
        this.statusQueryPort = statusQueryPort;
        this.statusActiveQueryPort = statusActiveQueryPort;
        this.statusSupplierActiveQueryPort = statusSupplierActiveQueryPort;
        this.updatedAtQueryPort = updatedAtQueryPort;
        this.statusCreatedAtQueryPort = statusCreatedAtQueryPort;
        this.aggregationQueryPort = aggregationQueryPort;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PurchaseOrderDto findById(String id, Boolean active) {
        return converter.convertToPurchaseOrderDto(findByIdAndActive(id, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public BigDecimal getTotalOrderedBySupplier(String supplierId) {
        return getTotalOrderedBySupplierInternal(supplierId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listByActive(Boolean active) {
        return converter.convertToPurchaseOrderSetDto(findAllByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listByStatus(PurchaseOrderStatus status) {
        return converter.convertToPurchaseOrderSetDto(findAllByStatus(status));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listByStatusAndActive(PurchaseOrderStatus status, Boolean active) {
        return converter.convertToPurchaseOrderSetDto(findAllByStatusAndActive(status, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listBySupplier(String supplierId) {
        return converter.convertToPurchaseOrderSetDto(findAllBySupplierId(supplierId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listBySupplierAndActive(String supplierId, Boolean active) {
        return converter.convertToPurchaseOrderSetDto(findAllBySupplierIdAndActive(supplierId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listByUpdatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active) {
        return converter.convertToPurchaseOrderSetDto(findAllByUpdatedAtBetweenAndActive(start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listByStatusAndCreatedAtBetweenAndActive(PurchaseOrderStatus status, LocalDateTime start, LocalDateTime end, Boolean active) {
        return converter.convertToPurchaseOrderSetDto(findAllByStatusAndCreatedAtBetweenAndActive(status, start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PurchaseOrderDto> listByStatusSupplierAndActive(PurchaseOrderStatus status, String supplierId, Boolean active) {
        return converter.convertToPurchaseOrderSetDto(findAllByStatusAndSupplierIdAndActive(status, supplierId, active));
    }


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected PurchaseOrder findByIdAndActive(String id, Boolean active) {
        return baseQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new PurchaseOrderNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllByActive(Boolean active) {
        return activeQueryPort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllByStatus(PurchaseOrderStatus status) {
        return statusQueryPort.findAllByStatus(status);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllByStatusAndActive(PurchaseOrderStatus status, Boolean active) {
        return statusActiveQueryPort.findAllByStatusAndActive(status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllBySupplierId(String supplierId) {
        return supplierQueryPort.findAllBySupplierId(supplierId);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllBySupplierIdAndActive(String supplierId, Boolean active) {
        return supplierActiveQueryPort.findAllBySupplierIdAndActive(supplierId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllByUpdatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active) {
        return updatedAtQueryPort.findAllByUpdatedAtBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllByStatusAndCreatedAtBetweenAndActive(PurchaseOrderStatus status, LocalDateTime start, LocalDateTime end, Boolean active) {
        return statusCreatedAtQueryPort.findAllByStatusAndCreatedAtBetweenAndActive(status, start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PurchaseOrder> findAllByStatusAndSupplierIdAndActive(PurchaseOrderStatus status, String supplierId, Boolean active) {
        return statusSupplierActiveQueryPort.findAllByStatusAndSupplierIdAndActive(status, supplierId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected BigDecimal getTotalOrderedBySupplierInternal(String supplierId) {
        return aggregationQueryPort.sumTotalBySupplier(supplierId);
    }
}
