package com.yasir.erp.minierp.modules.stockMovement.application.converter;

import com.yasir.erp.minierp.modules.product.application.converter.ProductStockMovementConverter;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StockMovementConverter {

    private final ProductStockMovementConverter productStockMovementConverter;

    public StockMovementConverter(ProductStockMovementConverter productStockMovementConverter) {
        this.productStockMovementConverter = productStockMovementConverter;
    }

    public StockMovementDto convertToDto(StockMovement movement) {

        return new StockMovementDto(
                movement.getId(),
                productStockMovementConverter.
                        convertToProductStockMovementDto(movement.getProduct()),
                movement.getQuantity(),
                movement.getMovementType(),
                movement.getDescription(),
                movement.getDate()
        );
    }

    public Set<StockMovementDto> convertToSetDto(Set<StockMovement> stockMovements){
        return stockMovements.stream().map(this::convertToDto).collect(Collectors.toSet());
    }


}
