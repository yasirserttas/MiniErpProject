package com.yasir.erp.minierp.modules.customer.application.service.command;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerConverter;
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.command.ActivateCustomerUseCase;
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
public class ActivateCustomerService implements ActivateCustomerUseCase {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final CustomerConverter customerConverter;

    public ActivateCustomerService(CustomerCommandPort customerCommandPort,
                                   CustomerActiveQueryPort customerActiveQueryPort,
                                   CustomerConverter customerConverter) {
        this.customerCommandPort = customerCommandPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.customerConverter = customerConverter;
    }

    @Override
    public CustomerDto activateCustomer(UUID customerId) {
        Customer existing = customerActiveQueryPort.findByIdAndActive(customerId, false)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Customer activated = new Customer(
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
                true
        );

        return customerConverter.convertToCustomer(customerCommandPort.save(activated));
    }
}
