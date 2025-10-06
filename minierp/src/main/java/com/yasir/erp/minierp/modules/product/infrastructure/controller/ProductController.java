package com.yasir.erp.minierp.modules.product.infrastructure.controller;


import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.application.dto.request.CreateProductDtoRequest;
import com.yasir.erp.minierp.modules.product.application.dto.request.UpdateProductDtoRequest;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.*;
import com.yasir.erp.minierp.modules.product.application.service.query.ProductQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final AddProductUseCase addProduct;
    private final UpdateProductUseCase updateProduct;
    private final ActivateProductUseCase activateProduct;
    private final DeactivateProductUseCase deactivateProduct;
    private final ReactivateProductUseCase reactivateProduct;
    private final DeleteProductUseCase deleteProduct;
    private final ProductQueryService queries;

    public ProductController(AddProductUseCase addProduct,
                             UpdateProductUseCase updateProduct,
                             ActivateProductUseCase activateProduct,
                             DeactivateProductUseCase deactivateProduct,
                             ReactivateProductUseCase reactivateProduct,
                             DeleteProductUseCase deleteProduct,
                             ProductQueryService queries) {
        this.addProduct = addProduct;
        this.updateProduct = updateProduct;
        this.activateProduct = activateProduct;
        this.deactivateProduct = deactivateProduct;
        this.reactivateProduct = reactivateProduct;
        this.deleteProduct = deleteProduct;
        this.queries = queries;
    }

    
    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody CreateProductDtoRequest req) {
        ProductDto created = addProduct.addProduct(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getByIdAndActive(@PathVariable String id,
                                                       @RequestParam Boolean active) {
        return ResponseEntity.ok(queries.findDtoByIdAndActive(id, active));
    }

    @GetMapping
    public ResponseEntity<Set<ProductDto>> listAll() {
        return ResponseEntity.ok(queries.findDtoByAll());
    }

    @GetMapping("/active")
    public ResponseEntity<Set<ProductDto>> listByActive(@RequestParam Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByActive(active));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Set<ProductDto>> listByUserAndActive(@PathVariable UUID userId,
                                                               @RequestParam Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByUserIdAndActive(userId, active));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable String id,
                                             @Valid @RequestBody UpdateProductDtoRequest req) {
        
        UpdateProductDtoRequest fixed = new UpdateProductDtoRequest(
                id,
                req.getName(),
                req.getDescription(),
                req.getPrice(),
                req.getVatRate(),
                req.getBrand(),
                req.getCategory(),
                req.getImageUrl()
        );
        return ResponseEntity.ok(updateProduct.updateProduct(fixed));
    }

    
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable String id) {
        activateProduct.activateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable String id) {
        deactivateProduct.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reactivate")
    public ResponseEntity<ProductDto> reactivate(@PathVariable String id) {
        return ResponseEntity.ok(reactivateProduct.reactivateProduct(id));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteProduct.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
