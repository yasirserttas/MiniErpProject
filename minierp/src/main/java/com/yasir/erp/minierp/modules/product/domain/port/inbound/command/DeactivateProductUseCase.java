package com.yasir.erp.minierp.modules.product.domain.port.inbound.command;

public interface DeactivateProductUseCase {
    void deactivateProduct(String productId);
}