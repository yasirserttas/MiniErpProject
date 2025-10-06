package com.yasir.erp.minierp.modules.stock.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;

public interface GetStockByIdUseCase {
    StockDto getStockDtoById(String stockId);
}