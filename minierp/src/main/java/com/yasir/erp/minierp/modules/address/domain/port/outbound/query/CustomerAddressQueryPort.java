package com.yasir.erp.minierp.modules.address.domain.port.outbound.query;


import com.yasir.erp.minierp.modules.address.domain.model.Address;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CustomerAddressQueryPort {
    Set<Address> findAllByCustomerIdAndActive(UUID customerId, boolean active);
    Optional<Address> findByIdAndCustomerIdAndActive
            (String addressId, UUID customerId, boolean active);
    boolean existsByIdAndCustomerIdAndActive(String addressId, UUID customerId, boolean active);
}
