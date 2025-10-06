package com.yasir.erp.minierp.modules.address.domain.port.inbound.command;


import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.user.CreateUserAddressDtoRequest;

public interface AddAddressToUserUseCase {
    AddressDto addAddressToUser(CreateUserAddressDtoRequest createUserAddressDtoRequest);
}
