package com.yasir.erp.minierp.modules.stock.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import java.util.Optional;

public interface StockProductQueryPort {
    Optional<Stock> findByProductId(String productId);
}