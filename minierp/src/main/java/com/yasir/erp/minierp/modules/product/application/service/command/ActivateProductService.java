package com.yasir.erp.minierp.modules.product.application.service.command;

import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.ActivateProductUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivateProductService implements ActivateProductUseCase {

    private final ProductCommandPort commandPort;
    private final ProductQueryPort queryPort;

    public ActivateProductService(ProductCommandPort commandPort, ProductQueryPort queryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void activateProduct(String productId) {
        Product existing = queryPort.findByIdAndActive(productId, false)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Product activated = new Product(
                existing.getId(), existing.getPublicId(), existing.getUser(),
                existing.getName(), existing.getDescription(),
                existing.getPrice(), existing.getVatRate(),
                existing.getBrand(), existing.getCategory(),
                true, existing.getImageUrl(),
                existing.getCreateAt(), LocalDateTime.now()
        );
        commandPort.save(activated);
    }
}
