package com.yasir.erp.minierp.modules.stock.application.service.command;

import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.DecreaseStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.command.StockCommandPort;
import com.yasir.erp.minierp.modules.stock.domain.port.outbound.query.StockProductQueryPort;
import com.yasir.erp.minierp.modules.stock.application.exception.StockNotFoundException;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.request.CreateStockMovementDtoRequest;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.AddStockMovementUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DecreaseStockService implements DecreaseStockUseCase {

    private final StockCommandPort commandPort;
    private final StockProductQueryPort stockProductQueryPort;
    private final AddStockMovementUseCase addStockMovementUseCase;

    public DecreaseStockService(StockCommandPort commandPort,
                                StockProductQueryPort stockProductQueryPort,
                                AddStockMovementUseCase addStockMovementUseCase) {
        this.commandPort = commandPort;
        this.stockProductQueryPort = stockProductQueryPort;
        this.addStockMovementUseCase = addStockMovementUseCase;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseStock(String productId, int quantity) {

        Stock stock = stockProductQueryPort.findByProductId(productId)
                .orElseThrow(() -> new StockNotFoundException(productId));

        int updatedQty = stock.getQuantity() - quantity;

        if (updatedQty < 0) {
            throw new IllegalArgumentException("Yetersiz stok! Kalan: " + stock.getQuantity());
        }

        Stock updated = new Stock(
                stock.getId(),
                stock.getProduct(),
                updatedQty,
                LocalDateTime.now()
        );

        String description = String.format(
                "%s (%s) isimli ürünün stoğu %d adet azaltıldı.",
                stock.getProduct().getName(),
                stock.getProduct().getBrand(),
                quantity
        );

        CreateStockMovementDtoRequest createStockMovementDtoRequest =
                new CreateStockMovementDtoRequest(
                        productId,
                        quantity,
                        StockMovementType.OUT,
                        description
                );

        addStockMovementUseCase.addStockMovement(createStockMovementDtoRequest);

        commandPort.save(updated);
    }
}
