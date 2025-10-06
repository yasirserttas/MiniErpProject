package com.yasir.erp.minierp.modules.stockMovement.application.service.query;

import com.yasir.erp.minierp.modules.stockMovement.application.converter.StockMovementConverter;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.stockMovement.application.excepiton.StockMovementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class StockMovementQueryService implements
        GetStockMovementByIdUseCase,
        ListAllStockMovementsUseCase,
        ListStockMovementsByProductIdUseCase,
        ListStockMovementsByDateBetweenUseCase,
        ListStockMovementsByMovementTypeUseCase,
        ListStockMovementsByProductIdAndDateBetweenUseCase,
        ListStockMovementsByProductIdAndMovementTypeUseCase,
        ListStockMovementsByMovementTypeAndDateBetweenUseCase,
        ListStockMovementsByProductIdDateBetweenAndMovementTypeUseCase {

    private final StockMovementConverter converter;
    private final StockMovementQueryPort baseQueryPort;
    private final StockMovementProductQueryPort productQueryPort;
    private final StockMovementDateQueryPort dateQueryPort;
    private final StockMovementTypeQueryPort typeQueryPort;
    private final StockMovementProductDateQueryPort productDateQueryPort;
    private final StockMovementProductTypeQueryPort productTypeQueryPort;
    private final StockMovementTypeDateQueryPort typeDateQueryPort;
    private final StockMovementProductDateTypeQueryPort productDateTypeQueryPort;

    public StockMovementQueryService(StockMovementConverter converter,
                                     StockMovementQueryPort baseQueryPort,
                                     StockMovementProductQueryPort productQueryPort,
                                     StockMovementDateQueryPort dateQueryPort,
                                     StockMovementTypeQueryPort typeQueryPort,
                                     StockMovementProductDateQueryPort productDateQueryPort,
                                     StockMovementProductTypeQueryPort productTypeQueryPort,
                                     StockMovementTypeDateQueryPort typeDateQueryPort,
                                     StockMovementProductDateTypeQueryPort productDateTypeQueryPort) {
        this.converter = converter;
        this.baseQueryPort = baseQueryPort;
        this.productQueryPort = productQueryPort;
        this.dateQueryPort = dateQueryPort;
        this.typeQueryPort = typeQueryPort;
        this.productDateQueryPort = productDateQueryPort;
        this.productTypeQueryPort = productTypeQueryPort;
        this.typeDateQueryPort = typeDateQueryPort;
        this.productDateTypeQueryPort = productDateTypeQueryPort;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public StockMovementDto getStockMovementDtoById(String id) {
        return converter.convertToDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findByDtoAll() {
        return converter.convertToSetDto(findAll());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByProductId(String productId) {
        return converter.convertToSetDto(findAllByProductId(productId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByDateBetween(LocalDateTime start, LocalDateTime end) {
        return converter.convertToSetDto(findAllByDateBetween(start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByMovementType(StockMovementType movementType) {
        return converter.convertToSetDto(findAllByMovementType(movementType));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByProductIdAndDateBetween
            (String productId, LocalDateTime start, LocalDateTime end) {
        return converter.convertToSetDto(findAllByProductIdAndDateBetween(productId, start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByProductIdAndMovementType
            (String productId, StockMovementType movementType) {
        return converter.convertToSetDto(findAllByProductIdAndMovementType(productId, movementType));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByMovementTypeAndDateBetween
            (StockMovementType movementType, LocalDateTime start, LocalDateTime end) {
        return converter.convertToSetDto(findAllByMovementTypeAndDateBetween(movementType, start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<StockMovementDto> findAllDtoByProductIdAndDateBetweenAndMovementType
            (String productId, LocalDateTime start, LocalDateTime end, StockMovementType movementType) {
        return converter.convertToSetDto
                (findAllByProductIdAndDateBetweenAndMovementType(productId, start, end, movementType));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected StockMovement findById(String id) {
        return baseQueryPort.findById(id)
                .orElseThrow(() -> new StockMovementNotFoundException
                        (id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<StockMovement> findAll() {
        return new HashSet<>(baseQueryPort.findAll());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<StockMovement> findAllByProductId(String productId) {
        return productQueryPort.findAllByProductId(productId);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<StockMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end) {
        return dateQueryPort.findAllByDateBetween(start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<StockMovement> findAllByMovementType(StockMovementType movementType) {
        return typeQueryPort.findAllByMovementType(movementType);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<StockMovement> findAllByProductIdAndDateBetween
            (String productId, LocalDateTime start, LocalDateTime end) {
        return productDateQueryPort.findAllByProductIdAndDateBetween(productId, start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<StockMovement> findAllByProductIdAndMovementType
            (String productId, StockMovementType movementType) {
        return productTypeQueryPort.findAllByProductIdAndMovementType(productId, movementType);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<StockMovement> findAllByMovementTypeAndDateBetween
            (StockMovementType movementType, LocalDateTime start, LocalDateTime end) {
        return typeDateQueryPort.findAllByMovementTypeAndDateBetween(movementType, start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<StockMovement> findAllByProductIdAndDateBetweenAndMovementType
            (String productId, LocalDateTime start, LocalDateTime end, StockMovementType movementType) {
        return productDateTypeQueryPort.
                findAllByProductIdAndDateBetweenAndMovementType(productId, start, end, movementType);
    }
}
