package com.yasir.erp.minierp.modules.product.application.service.query;

import com.yasir.erp.minierp.modules.product.application.converter.ProductConverter;
import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.query.FindProductByIdAndActiveUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.query.ListAllProductsUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.query.ListProductsByActiveUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.query.ListProductsByUserAndActiveUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductQueryService implements
        FindProductByIdAndActiveUseCase,
        ListAllProductsUseCase,
        ListProductsByActiveUseCase,
        ListProductsByUserAndActiveUseCase {

    private final ProductQueryPort queryPort;
    private final ProductConverter converter;

    public ProductQueryService(ProductQueryPort queryPort, ProductConverter converter) {
        this.queryPort = queryPort;
        this.converter = converter;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductDto findDtoByIdAndActive(String productId, Boolean active) {
        return converter.convertToProductDto(findByIdAndActive(productId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ProductDto> findDtoByAll() {
        return converter.convertToProductDtoSet(findByAll());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ProductDto> findDtoAllByActive(Boolean active) {
        return converter.convertToProductDtoSet(findAllByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ProductDto> findDtoAllByUserIdAndActive(UUID userId, Boolean active) {
        return converter.convertToProductDtoSet(findAllByUserIdAndActive(userId, active));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Product findByIdAndActive(String productId, Boolean active) {
        return queryPort.findByIdAndActive(productId, active)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Product> findByAll() {
        return new HashSet<>(queryPort.findAll());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Product> findAllByActive(Boolean active) {
        return queryPort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Product> findAllByUserIdAndActive(UUID userId, Boolean active) {
        return queryPort.findAllByUserIdAndActive(userId, active);
    }
}
