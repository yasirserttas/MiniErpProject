package com.yasir.erp.minierp.modules.stock.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;
import com.yasir.erp.minierp.modules.stock.application.converter.request.UpdateStockDtoRequest;

public interface UpdateStockUseCase {
    StockDto updateStock(UpdateStockDtoRequest req);
}