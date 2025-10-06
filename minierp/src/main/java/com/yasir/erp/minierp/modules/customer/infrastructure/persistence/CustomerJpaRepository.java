package com.yasir.erp.minierp.modules.customer.infrastructure.persistence;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, UUID> {
    Set<Customer> findAllByUserId(UUID userId);
    Set<Customer> findAllByActive(boolean active);
    Set<Customer> findAllByUserIdAndActive(UUID userId, boolean active);
    Set<Customer> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Optional<Customer> findByIdAndActive(UUID id, boolean active);
}
