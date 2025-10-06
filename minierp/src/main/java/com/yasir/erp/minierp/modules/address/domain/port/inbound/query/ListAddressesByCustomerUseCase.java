package com.yasir.erp.minierp.modules.address.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;

import java.util.Set;
import java.util.UUID;

public interface ListAddressesByCustomerUseCase {
    Set<AddressDto> list(UUID customerId, boolean active);
}
