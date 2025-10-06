package com.yasir.erp.minierp.modules.product.application.service.command;

import com.yasir.erp.minierp.modules.product.application.converter.ProductConverter;
import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.ReactivateProductUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReactivateProductService implements ReactivateProductUseCase {

    private final ProductCommandPort commandPort;
    private final ProductQueryPort queryPort;
    private final ProductConverter converter;

    public ReactivateProductService(ProductCommandPort commandPort,
                                    ProductQueryPort queryPort,
                                    ProductConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto reactivateProduct(String productId) {
        Product existing = queryPort.findByIdAndActive(productId, false)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Product activated = new Product(
                existing.getId(), existing.getPublicId(), existing.getUser(),
                existing.getName(), existing.getDescription(), existing.getPrice(), existing.getVatRate(),
                existing.getBrand(), existing.getCategory(),
                true, existing.getImageUrl(),
                existing.getCreateAt(), LocalDateTime.now()
        );
        return converter.convertToProductDto(commandPort.save(activated));
    }
}
