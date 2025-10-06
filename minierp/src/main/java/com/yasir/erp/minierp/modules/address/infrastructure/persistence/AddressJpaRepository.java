package com.yasir.erp.minierp.modules.address.infrastructure.persistence;


import com.yasir.erp.minierp.modules.address.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AddressJpaRepository extends JpaRepository<Address, String> {

    Set<Address> findAllByCustomer_IdAndCustomer_Active(UUID customerId, boolean active);
    Set<Address> findAllByUser_IdAndUser_Active(UUID userId, boolean active);
    Set<Address> findAllBySupplier_IdAndSupplier_Active(String supplierId, boolean active);

    Optional<Address> findByIdAndCustomer_IdAndCustomer_Active
    (String addressId, UUID customerId, boolean active);
    Optional<Address> findByIdAndUser_IdAndUser_Active
            (String addressId, UUID userId, boolean active);
    Optional<Address> findByIdAndSupplier_IdAndSupplier_Active
            (String addressId, String supplierId, boolean active);

    boolean existsByIdAndCustomer_IdAndCustomer_Active
    (String addressId, UUID customerId, boolean active);
    boolean existsByIdAndUser_IdAndUser_Active
            (String addressId, UUID userId, boolean active);
    boolean existsByIdAndSupplier_IdAndSupplier_Active
            (String addressId, String supplierId, boolean active);

}
