package com.yasir.erp.minierp.modules.stock.application.service.query;

import com.yasir.erp.minierp.modules.stock.application.converter.StockConverter;
import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;
import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.query.GetStockByIdUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.query.GetStockByProductIdUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockProductQueryPort;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockQueryPort;
import com.yasir.erp.minierp.modules.stock.application.exception.StockNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockQueryService implements
        GetStockByIdUseCase,
        GetStockByProductIdUseCase {

    private final StockConverter converter;
    private final StockQueryPort stockQueryPort;
    private final StockProductQueryPort stockProductQueryPort;

    public StockQueryService(StockConverter converter,
                             StockQueryPort stockQueryPort,
                             StockProductQueryPort stockProductQueryPort) {
        this.converter = converter;
        this.stockQueryPort = stockQueryPort;
        this.stockProductQueryPort = stockProductQueryPort;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public StockDto getStockDtoById(String id) {
        return converter.convertToDto(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public StockDto getStockDtoByProductId(String productId) {
        return converter.convertToDto(findEntityByProductId(productId));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Stock findEntityById(String id) {
        return stockQueryPort.findById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Stock findEntityByProductId(String productId) {
        return stockProductQueryPort.findByProductId(productId)
                .orElseThrow(() -> new StockNotFoundException( productId));
    }
}
