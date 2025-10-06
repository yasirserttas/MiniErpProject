package com.yasir.erp.minierp.modules.product.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import java.util.Set;
import java.util.UUID;

public interface ListProductsByUserAndActiveUseCase {
    Set<ProductDto> findDtoAllByUserIdAndActive(UUID userId, Boolean active);
}