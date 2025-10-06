package com.yasir.erp.minierp.modules.address.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.address.domain.model.Address;

import java.util.Optional;

public interface AddressQueryPort {
    Optional<Address> findById(String addressId);

}
