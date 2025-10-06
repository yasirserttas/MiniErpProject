package com.yasir.erp.minierp.modules.product.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;

public interface FindProductByIdAndActiveUseCase {
    ProductDto findDtoByIdAndActive(String productId, Boolean active);
}