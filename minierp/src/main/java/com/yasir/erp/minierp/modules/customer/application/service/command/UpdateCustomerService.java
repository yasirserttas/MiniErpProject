package com.yasir.erp.minierp.modules.customer.application.service.command;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerConverter;
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.application.dto.request.UpdateCustomerDtoRequest;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.command.UpdateCustomerUseCase;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.command.CustomerCommandPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final CustomerConverter customerConverter;


    public UpdateCustomerService(CustomerCommandPort customerCommandPort,
                                 CustomerActiveQueryPort customerActiveQueryPort,
                                 CustomerConverter customerConverter) {
        this.customerCommandPort = customerCommandPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.customerConverter = customerConverter;
    }

    @Override
    public CustomerDto updateCustomer(UpdateCustomerDtoRequest req) {
        Customer existing = customerActiveQueryPort.findByIdAndActive(req.getId(),true)
                .orElseThrow(() -> new CustomerNotFoundException(req.getId()));

        Customer updated = new Customer(
                existing.getId(),
                req.getName(),
                req.getSurName(),
                req.getCompanyName(),
                req.getPhoneNumber(),
                req.getEmail(),
                req.getTaxNumber(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                existing.getUser(),
                existing.getAddresses(),
                existing.getOrders(),
                true
        );

        return customerConverter.convertToCustomer(customerCommandPort.save(updated));
    }
}
