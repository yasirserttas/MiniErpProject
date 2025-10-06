package com.yasir.erp.minierp.modules.stockMovement.application.service.command;

import com.yasir.erp.minierp.modules.stockMovement.application.excepiton.StockMovementNotFoundException;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.DeleteStockMovementByIdUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.command.StockMovementCommandPort;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query.StockMovementQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteStockMovementByIdService implements DeleteStockMovementByIdUseCase {

    private final StockMovementCommandPort commandPort;
    private final StockMovementQueryPort queryPort;

    public DeleteStockMovementByIdService(StockMovementCommandPort commandPort,
                                          StockMovementQueryPort queryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStockMovementById(String id) {

        StockMovement existing = queryPort.findById(id)
                .orElseThrow(() -> new StockMovementNotFoundException
                        (id));

        commandPort.delete(existing);
    }
}