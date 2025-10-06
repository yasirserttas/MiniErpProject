package com.yasir.erp.minierp.modules.address.application.service.command;

import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.customer.UpdateCustomerAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.application.exception.AddressNotFoundException;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.UpdateAddressUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.command.AddressCommandPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.AddressQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateAddressService implements UpdateAddressUseCase {

    private final AddressCommandPort addressCommandPort;
    private final AddressQueryPort addressQueryPort;
    private final AddressConverter addressConverter;

    public UpdateAddressService(AddressCommandPort addressCommandPort,
                                AddressQueryPort addressQueryPort,
                                AddressConverter addressConverter) {
        this.addressCommandPort = addressCommandPort;
        this.addressQueryPort = addressQueryPort;
        this.addressConverter = addressConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressDto updateAddress(UpdateCustomerAddressDtoRequest updateCustomerAddressDtoRequest) {
        Address existing = addressQueryPort
                .findById(updateCustomerAddressDtoRequest.getAddressId()).
                orElseThrow(()-> new AddressNotFoundException
                        (updateCustomerAddressDtoRequest.getAddressId()));

        Address updated = new Address(
                existing.getId(),
                updateCustomerAddressDtoRequest.getStreet(),
                updateCustomerAddressDtoRequest.getDistrict(),
                updateCustomerAddressDtoRequest.getCity(),
                updateCustomerAddressDtoRequest.getCountry(),
                updateCustomerAddressDtoRequest.getPostalCode(),
                existing.getUser(),
                existing.getCustomer(),
                existing.getSupplier()
        );
        return addressConverter.convertToAddress(addressCommandPort.save(updated));
    }
}
