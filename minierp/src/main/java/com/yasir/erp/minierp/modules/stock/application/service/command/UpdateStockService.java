package com.yasir.erp.minierp.modules.stock.application.service.command;

import com.yasir.erp.minierp.modules.stock.application.converter.StockConverter;
import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;
import com.yasir.erp.minierp.modules.stock.application.converter.request.UpdateStockDtoRequest;
import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.UpdateStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.command.StockCommandPort;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockQueryPort;
import com.yasir.erp.minierp.modules.stock.application.exception.StockNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateStockService implements UpdateStockUseCase {

    private final StockCommandPort commandPort;
    private final StockQueryPort queryPort;
    private final StockConverter converter;

    public UpdateStockService(StockCommandPort commandPort,
                              StockQueryPort queryPort,
                              StockConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StockDto updateStock(UpdateStockDtoRequest updateStockDtoRequest) {

        Stock existing = queryPort.findById(updateStockDtoRequest.getId())
                .orElseThrow(() ->
                        new StockNotFoundException(updateStockDtoRequest.getId()));

        Stock updated = new Stock(
                existing.getId(),
                existing.getProduct(),
                updateStockDtoRequest.getQuantity(),
                LocalDateTime.now()
        );

        return converter.convertToDto(commandPort.save(updated));
    }
}
