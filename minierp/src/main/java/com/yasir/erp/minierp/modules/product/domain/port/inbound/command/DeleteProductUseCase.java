package com.yasir.erp.minierp.modules.product.domain.port.inbound.command;

public interface DeleteProductUseCase {
    void deleteProductById(String productId);
}