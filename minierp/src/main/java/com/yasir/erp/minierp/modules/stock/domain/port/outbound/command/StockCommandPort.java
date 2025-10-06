package com.yasir.erp.minierp.modules.stock.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.stock.domain.model.Stock;

public interface StockCommandPort {
    Stock save(Stock s);
    void delete(Stock s);
}