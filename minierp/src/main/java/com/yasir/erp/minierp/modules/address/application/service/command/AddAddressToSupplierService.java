package com.yasir.erp.minierp.modules.address.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.supplier.CreateSupplierAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.AddAddressToSupplierUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.command.AddressCommandPort;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByIdAndActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddAddressToSupplierService implements AddAddressToSupplierUseCase {

    private final AddressCommandPort addressCommandPort;
    private final SupplierByIdAndActiveQueryPort supplierByIdAndActiveQueryPort;
    private final AddressConverter addressConverter;

    public AddAddressToSupplierService(AddressCommandPort addressCommandPort,
                                       SupplierByIdAndActiveQueryPort supplierByIdAndActiveQueryPort,
                                       AddressConverter addressConverter) {
        this.addressCommandPort = addressCommandPort;
        this.supplierByIdAndActiveQueryPort = supplierByIdAndActiveQueryPort;
        this.addressConverter = addressConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressDto addAddressToSupplier
            (CreateSupplierAddressDtoRequest createSupplierAddressDtoRequest) {
        Supplier supplier = supplierByIdAndActiveQueryPort.findByIdAndActive
                        (createSupplierAddressDtoRequest.getSupplierId(), true)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Supplier not found or inactive: "
                                + createSupplierAddressDtoRequest.getSupplierId()));

        Address address = new Address(
                UlidGenerator.generate(),
                createSupplierAddressDtoRequest.getStreet(),
                createSupplierAddressDtoRequest.getDistrict(),
                createSupplierAddressDtoRequest.getCity(),
                createSupplierAddressDtoRequest.getCountry(),
                createSupplierAddressDtoRequest.getPostalCode(),
                null,
                null,
                supplier
        );
        return addressConverter.convertToAddress(addressCommandPort.save(address));
    }
}
