package com.yasir.erp.minierp.modules.stockMovement.application.service.command;

import com.yasir.erp.minierp.modules.stockMovement.application.excepiton.StockMovementNotFoundException;
import com.yasir.erp.minierp.modules.stockMovement.application.converter.StockMovementConverter;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.request.UpdateStockMovementDtoRequest;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.UpdateStockMovementUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.command.StockMovementCommandPort;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query.StockMovementQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateStockMovementService implements UpdateStockMovementUseCase {

    private final StockMovementCommandPort commandPort;
    private final StockMovementQueryPort queryPort;
    private final StockMovementConverter converter;

    public UpdateStockMovementService(StockMovementCommandPort commandPort,
                                      StockMovementQueryPort queryPort,
                                      StockMovementConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StockMovementDto updateStockMovement
            (UpdateStockMovementDtoRequest updateStockMovementDtoRequest) {

        StockMovement existing = queryPort.findById(updateStockMovementDtoRequest.getId())
                .orElseThrow(() -> new StockMovementNotFoundException
                        ( updateStockMovementDtoRequest.getId()));

        StockMovement updated = new StockMovement(
                existing.getId(),
                existing.getProduct(),
                updateStockMovementDtoRequest.getQuantity(),
                updateStockMovementDtoRequest.getMovementType(),
                updateStockMovementDtoRequest.getDescription(),
                LocalDateTime.now()
        );

        return converter.convertToDto(commandPort.save(updated));
    }
}