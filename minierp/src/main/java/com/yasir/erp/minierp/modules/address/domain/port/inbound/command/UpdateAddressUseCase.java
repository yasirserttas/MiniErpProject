package com.yasir.erp.minierp.modules.address.domain.port.inbound.command;


import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.application.dto.request.customer.UpdateCustomerAddressDtoRequest;

public interface UpdateAddressUseCase {
    AddressDto updateAddress(UpdateCustomerAddressDtoRequest updateCustomerAddressDtoRequest);
}
