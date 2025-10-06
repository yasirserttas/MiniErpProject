package com.yasir.erp.minierp.modules.stock.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.command.StockCommandPort;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockProductQueryPort;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockQueryPort;
import com.yasir.erp.minierp.modules.stock.infrastructure.persistence.StockJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class StockJpaAdapter implements
        StockCommandPort,
        StockQueryPort,
        StockProductQueryPort {

    private final StockJpaRepository stockJpaRepository;

    public StockJpaAdapter(StockJpaRepository stockJpaRepository) {
        this.stockJpaRepository = stockJpaRepository;
    }

    @Override
    public Stock save(Stock stock) {
        return stockJpaRepository.save(stock);
    }

    @Override
    public void delete(Stock stock) {
        stockJpaRepository.delete(stock);
    }

    @Override
    public Optional<Stock> findById(String id) {
        return stockJpaRepository.findById(id);
    }

    @Override
    public Optional<Stock> findByProductId(String productId) {
        return stockJpaRepository.findByProductId(productId);
    }
}