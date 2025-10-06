package com.yasir.erp.minierp.modules.product.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.application.dto.request.UpdateProductDtoRequest;

public interface UpdateProductUseCase {
    ProductDto updateProduct(UpdateProductDtoRequest req);
}