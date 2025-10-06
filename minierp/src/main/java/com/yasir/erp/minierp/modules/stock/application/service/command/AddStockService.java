package com.yasir.erp.minierp.modules.stock.application.service.command;

import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.stock.application.converter.StockConverter;
import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;
import com.yasir.erp.minierp.modules.stock.application.converter.request.CreateStockDtoRequest;
import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.AddStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.command.StockCommandPort;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddStockService implements AddStockUseCase {

    private final StockCommandPort commandPort;
    private final StockConverter converter;
    private final ProductQueryPort productQueryPort;

    public AddStockService(StockCommandPort commandPort,
                           StockConverter converter,
                           ProductQueryPort productQueryPort) {
        this.commandPort = commandPort;
        this.converter = converter;
        this.productQueryPort = productQueryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StockDto addStock(CreateStockDtoRequest createStockDtoRequest) {

      Product product =  productQueryPort.
              findByIdAndActive(createStockDtoRequest.getProductId(),true)
                .orElseThrow(()-> new ProductNotFoundException(createStockDtoRequest.getProductId()));

         Stock stock = new Stock(
                UlidGenerator.generate(),
                product,
                 createStockDtoRequest.getQuantity(),
                LocalDateTime.now()
        );

        Stock saved = commandPort.save(stock);
        return converter.convertToDto(saved);
    }
}
