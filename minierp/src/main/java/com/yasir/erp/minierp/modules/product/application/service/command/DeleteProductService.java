package com.yasir.erp.minierp.modules.product.application.service.command;

import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.DeleteProductUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteProductService implements DeleteProductUseCase {

    private final ProductCommandPort commandPort;
    private final ProductQueryPort queryPort;

    public DeleteProductService(ProductCommandPort commandPort, ProductQueryPort queryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteProductById(String productId) {
        Product product = queryPort.findByIdAndActive(productId, false)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        commandPort.delete(product);
    }
}
