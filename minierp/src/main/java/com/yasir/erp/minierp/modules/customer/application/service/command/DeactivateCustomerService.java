package com.yasir.erp.minierp.modules.customer.application.service.command;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerConverter;
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.command.DeactivateCustomerUseCase;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.command.CustomerCommandPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeactivateCustomerService implements DeactivateCustomerUseCase {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final CustomerConverter customerConverter;

    public DeactivateCustomerService(CustomerCommandPort customerCommandPort,
                                     CustomerActiveQueryPort customerActiveQueryPort,
                                     CustomerConverter customerConverter) {
        this.customerCommandPort = customerCommandPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.customerConverter = customerConverter;
    }

    @Override
    public CustomerDto deactivateCustomer(UUID customerId) {
        Customer existing = customerActiveQueryPort.findByIdAndActive(customerId, true)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Customer deactivated = new Customer(
                existing.getId(),
                existing.getName(),
                existing.getSurName(),
                existing.getCompanyName(),
                existing.getPhoneNumber(),
                existing.getEmail(),
                existing.getTaxNumber(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                existing.getUser(),
                existing.getAddresses(),
                existing.getOrders(),
                false
        );

        return customerConverter.convertToCustomer(customerCommandPort.save(deactivated));
    }
}
