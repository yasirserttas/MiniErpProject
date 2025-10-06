package com.yasir.erp.minierp.modules.product.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;

public interface ReactivateProductUseCase {
    ProductDto reactivateProduct(String productId);
}