package com.yasir.erp.minierp.modules.customer.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.command.CustomerCommandPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerCreatedAtQueryPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerQueryPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerUserQueryPort;
import com.yasir.erp.minierp.modules.customer.infrastructure.persistence.CustomerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class CustomerJpaAdapter implements CustomerCommandPort, CustomerQueryPort,
        CustomerActiveQueryPort,
        CustomerUserQueryPort,
        CustomerCreatedAtQueryPort{

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerJpaAdapter(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }


    @Override
    public Customer save(Customer customer) {
        return customerJpaRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerJpaRepository.delete(customer);
    }

    @Override
    public Optional<Customer> findByIdAndActive(UUID id, boolean active) {
        return customerJpaRepository.findByIdAndActive(id,active);
    }

    @Override
    public Set<Customer> findAllByActive(boolean active) {
        return customerJpaRepository.findAllByActive(active);
    }

    @Override
    public Set<Customer> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return customerJpaRepository.findAllByCreatedAtBetweenAndActive(start,end,active);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerJpaRepository.findById(id);
    }

    @Override
    public Set<Customer> findAllByUserId(UUID userId) {
        return customerJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Set<Customer> findAllByUserIdAndActive(UUID userId, boolean active) {
        return customerJpaRepository.findAllByUserIdAndActive(userId,active);
    }
}
