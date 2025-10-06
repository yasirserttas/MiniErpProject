package com.yasir.erp.minierp.modules.stock.application.service.command;

import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.DeleteStockByIdUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.command.StockCommandPort;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockQueryPort;
import com.yasir.erp.minierp.modules.stock.application.exception.StockNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteStockByIdService implements DeleteStockByIdUseCase {

    private final StockCommandPort commandPort;
    private final StockQueryPort queryPort;

    public DeleteStockByIdService(StockCommandPort commandPort,
                                  StockQueryPort queryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStockById(String stockId) {
        Stock existing = queryPort.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));

        commandPort.delete(existing);
    }
}
