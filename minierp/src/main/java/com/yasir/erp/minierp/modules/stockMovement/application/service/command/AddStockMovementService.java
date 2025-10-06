package com.yasir.erp.minierp.modules.stockMovement.application.service.command;

import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.stockMovement.application.converter.StockMovementConverter;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.request.CreateStockMovementDtoRequest;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.AddStockMovementUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.command.StockMovementCommandPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddStockMovementService implements AddStockMovementUseCase {

    private final StockMovementCommandPort commandPort;
    private final StockMovementConverter converter;
    private final ProductQueryPort productQueryPort;

    public AddStockMovementService(StockMovementCommandPort commandPort,
                                   StockMovementConverter converter,
                                   ProductQueryPort productQueryPort) {
        this.commandPort = commandPort;
        this.converter = converter;
        this.productQueryPort = productQueryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StockMovementDto addStockMovement(CreateStockMovementDtoRequest
                                                         createStockMovementDtoRequest) {

        Product product = productQueryPort.findByIdAndActive
                        (createStockMovementDtoRequest.getProductId(), true)
                .orElseThrow(()-> new ProductNotFoundException
                        (createStockMovementDtoRequest.getProductId()));


        StockMovement movement = new StockMovement(
                UlidGenerator.generate(),
                product,
                createStockMovementDtoRequest.getQuantity(),
                createStockMovementDtoRequest.getMovementType(),
                createStockMovementDtoRequest.getDescription(),
                LocalDateTime.now()
        );

        return converter.convertToDto(commandPort.save(movement));
    }
}