package com.yasir.erp.minierp.modules.stock.application.converter;

import com.yasir.erp.minierp.modules.product.application.converter.ProductStockConverter;
import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;
import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StockConverter {

    private final ProductStockConverter productStockConverter;

    public StockConverter(ProductStockConverter productStockConverter) {
        this.productStockConverter = productStockConverter;
    }

    public StockDto convertToDto(Stock stock) {

        return new StockDto(
                stock.getId(),
                productStockConverter.convertToProductStockDto(stock.getProduct()),
                stock.getQuantity(),
                stock.getLastUpdated()
        );
    }

    public Set<StockDto> convertToSetDto(Set<Stock> stocks){
        return stocks.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

}
