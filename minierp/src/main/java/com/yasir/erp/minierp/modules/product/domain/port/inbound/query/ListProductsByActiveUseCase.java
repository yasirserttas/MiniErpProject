package com.yasir.erp.minierp.modules.product.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import java.util.Set;

public interface ListProductsByActiveUseCase {
    Set<ProductDto> findDtoAllByActive(Boolean active);
}