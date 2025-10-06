package com.yasir.erp.minierp.modules.address.domain.port.outbound.query;


import com.yasir.erp.minierp.modules.address.domain.model.Address;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserAddressQueryPort {
    Set<Address> findAllByUserIdAndActive(UUID userId, boolean active);
    Optional<Address> findByIdAndUserIdAndActive(String addressId, UUID userId, boolean active);
    boolean existsByIdAndUserIdAndActive(String addressId, UUID userId, boolean active);
}
