package com.yasir.erp.minierp.modules.customer.application.service.query;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerConverter;
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CustomerQueryService implements
        FindCustomerByIdUseCase,
        ListCustomersByActiveUseCase,
        ListCustomersByCreatedAtUseCase,
        ListCustomersByUserAndActiveUseCase,
        ListCustomersByUserUseCase {

    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final CustomerCreatedAtQueryPort customerCreatedAtQueryPort;
    private final CustomerUserQueryPort customerUserQueryPort;
    private final CustomerConverter customerConverter;

    public CustomerQueryService(CustomerActiveQueryPort customerActiveQueryPort,
                                CustomerCreatedAtQueryPort customerCreatedAtQueryPort,
                                CustomerUserQueryPort customerUserQueryPort,
                                CustomerConverter customerConverter) {
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.customerCreatedAtQueryPort = customerCreatedAtQueryPort;
        this.customerUserQueryPort = customerUserQueryPort;
        this.customerConverter = customerConverter;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CustomerDto findDtoByIdAndActive(UUID id, boolean active) {
        return customerConverter.convertToCustomer(findByIdAndActive(id, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<CustomerDto> findDtoAllByActive(boolean active) {
        return customerConverter.convertToCustomerSet(findAllByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<CustomerDto> findDtoAllByCreatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active) {
        return customerConverter.convertToCustomerSet(
                findAllByCreatedAtBetweenAndActive(start, end, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<CustomerDto> findDtoAllByUserIdAndActive(UUID userId, boolean active) {
        return customerConverter.convertToCustomerSet(findAllByUserIdAndActive(userId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<CustomerDto> findDtoAllByUserId(UUID userId) {
        return customerConverter.convertToCustomerSet(findAllByUserId(userId));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Customer findByIdAndActive(UUID id, boolean active) {
        return customerActiveQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Customer> findAllByActive(boolean active) {
        return customerActiveQueryPort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Customer> findAllByCreatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active) {
        return customerCreatedAtQueryPort.findAllByCreatedAtBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Customer> findAllByUserIdAndActive(UUID userId, boolean active) {
        return customerUserQueryPort.findAllByUserIdAndActive(userId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Customer> findAllByUserId(UUID userId) {
        return customerUserQueryPort.findAllByUserId(userId);
    }
}
