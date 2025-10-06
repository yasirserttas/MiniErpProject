package com.yasir.erp.minierp.modules.product.application.service.command;

import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.DeactivateProductUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeactivateProductService implements DeactivateProductUseCase {

    private final ProductCommandPort commandPort;
    private final ProductQueryPort queryPort;

    public DeactivateProductService(ProductCommandPort commandPort, ProductQueryPort queryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deactivateProduct(String productId) {
        Product existing = queryPort.findByIdAndActive(productId, true)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Product deactivated = new Product(
                existing.getId(), existing.getPublicId(), existing.getUser(),
                existing.getName(), existing.getDescription(), existing.getPrice(), existing.getVatRate(),
                existing.getBrand(), existing.getCategory(),
                false, existing.getImageUrl(),
                existing.getCreateAt(), LocalDateTime.now()
        );
        commandPort.save(deactivated);
    }
}
