package com.yasir.erp.minierp.modules.address.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.user.CreateUserAddressDtoRequest;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.AddAddressToUserUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.command.AddressCommandPort;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddAddressToUserService implements AddAddressToUserUseCase {

    private final AddressCommandPort addressCommandPort;
    private final UserByIdQueryPort userQueryPort;
    private final AddressConverter addressConverter;

    public AddAddressToUserService(AddressCommandPort addressCommandPort,
                                   UserByIdQueryPort userQueryPort,
                                   AddressConverter addressConverter) {
        this.addressCommandPort = addressCommandPort;
        this.userQueryPort = userQueryPort;
        this.addressConverter = addressConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressDto addAddressToUser(CreateUserAddressDtoRequest createUserAddressDtoRequest) {

        User user = userQueryPort.findById(createUserAddressDtoRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found or inactive: " + createUserAddressDtoRequest.getUserId()
                ));

        Address address = new Address(
                UlidGenerator.generate(),
                createUserAddressDtoRequest.getStreet(),
                createUserAddressDtoRequest.getDistrict(),
                createUserAddressDtoRequest.getCity(),
                createUserAddressDtoRequest.getCountry(),
                createUserAddressDtoRequest.getPostalCode(),
                user,
                null,
                null
        );


        return addressConverter.convertToAddress(addressCommandPort.save(address));
    }
}
