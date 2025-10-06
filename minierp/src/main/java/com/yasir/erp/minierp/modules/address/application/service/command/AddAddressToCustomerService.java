package com.yasir.erp.minierp.modules.address.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.customer.CreateCustomerAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.AddAddressToCustomerUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.command.AddressCommandPort;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddAddressToCustomerService implements AddAddressToCustomerUseCase {

    private final AddressCommandPort addressCommandPort;
    private final CustomerQueryPort customerQueryPort;
    private final AddressConverter addressConverter;

    public AddAddressToCustomerService(AddressCommandPort addressCommandPort,
                                       CustomerQueryPort customerQueryPort,
                                       AddressConverter addressConverter) {
        this.addressCommandPort = addressCommandPort;
        this.customerQueryPort = customerQueryPort;
        this.addressConverter = addressConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressDto addAddressToCustomer
            (CreateCustomerAddressDtoRequest createCustomerAddressDtoRequest) {

        Customer customer = customerQueryPort.findById
                        (createCustomerAddressDtoRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer not found or inactive: " +
                                createCustomerAddressDtoRequest.getCustomerId()));

        Address address = new Address(
                UlidGenerator.generate(),
                createCustomerAddressDtoRequest.getStreet(),
                createCustomerAddressDtoRequest.getDistrict(),
                createCustomerAddressDtoRequest.getCity(),
                createCustomerAddressDtoRequest.getCountry(),
                createCustomerAddressDtoRequest.getPostalCode(),
                null,
                customer,
                null
        );
        return addressConverter.convertToAddress(addressCommandPort.save(address));
    }
}
