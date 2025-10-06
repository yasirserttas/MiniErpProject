package com.yasir.erp.minierp.modules.address.infrastructure.controller;

import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.customer.CreateCustomerAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.application.dto.request.customer.UpdateCustomerAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.application.dto.request.supplier.CreateSupplierAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.application.dto.request.user.CreateUserAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.application.service.query.AddressQueryService;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.AddAddressToCustomerUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.AddAddressToSupplierUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.AddAddressToUserUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.DeleteAddressUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.UpdateAddressUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddAddressToCustomerUseCase addAddressToCustomer;
    private final AddAddressToSupplierUseCase addAddressToSupplier;
    private final AddAddressToUserUseCase addAddressToUser;
    private final UpdateAddressUseCase updateAddress;
    private final DeleteAddressUseCase deleteAddress;
    private final AddressQueryService addressQueries;

    public AddressController(AddAddressToCustomerUseCase addAddressToCustomer,
                             AddAddressToSupplierUseCase addAddressToSupplier,
                             AddAddressToUserUseCase addAddressToUser,
                             UpdateAddressUseCase updateAddress,
                             DeleteAddressUseCase deleteAddress,
                             AddressQueryService addressQueries) {
        this.addAddressToCustomer = addAddressToCustomer;
        this.addAddressToSupplier = addAddressToSupplier;
        this.addAddressToUser = addAddressToUser;
        this.updateAddress = updateAddress;
        this.deleteAddress = deleteAddress;
        this.addressQueries = addressQueries;
    }

    @PostMapping("/customer")
    public ResponseEntity<AddressDto> addToCustomer(@Valid @RequestBody
                                                        CreateCustomerAddressDtoRequest req) {
        AddressDto created = addAddressToCustomer.addAddressToCustomer(req);
        URI location = buildLocation(created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/supplier")
    public ResponseEntity<AddressDto> addToSupplier(@Valid @RequestBody
                                                        CreateSupplierAddressDtoRequest
                                                            createSupplierAddressDtoRequest) {
        AddressDto created = addAddressToSupplier.addAddressToSupplier(createSupplierAddressDtoRequest);
        URI location = buildLocation(created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/user")
    public ResponseEntity<AddressDto> addToUser(@Valid @RequestBody CreateUserAddressDtoRequest
                                                        createUserAddressDtoRequest) {
        AddressDto created = addAddressToUser.addAddressToUser(createUserAddressDtoRequest);
        URI location = buildLocation(created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getById(@PathVariable String addressId) {
        return ResponseEntity.ok(addressQueries.findDtoById(addressId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Set<AddressDto>> listByCustomer(@PathVariable UUID customerId,
                                                          @RequestParam(defaultValue = "true")
                                                          boolean active) {
        return ResponseEntity.ok(addressQueries.findDtoAllByCustomer_IdAndCustomer_Active(customerId, active));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<AddressDto>> listByUser(@PathVariable UUID userId,
                                                      @RequestParam(defaultValue = "true")
                                                      boolean active) {
        return ResponseEntity.ok(addressQueries.findDtoAllByUser_IdAndUser_Active(userId, active));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<Set<AddressDto>> listBySupplier(@PathVariable String supplierId,
                                                          @RequestParam(defaultValue = "true")
                                                          boolean active) {
        return ResponseEntity.ok(addressQueries.findDtoAllBySupplier_IdAndSupplier_Active(supplierId, active));
    }

    @PutMapping
    public ResponseEntity<AddressDto> update(@Valid @RequestBody UpdateCustomerAddressDtoRequest
                                                     updateCustomerAddressDtoRequest) {
        return ResponseEntity.ok(updateAddress.updateAddress(updateCustomerAddressDtoRequest));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable String addressId) {
        deleteAddress.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    private static URI buildLocation(String id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
