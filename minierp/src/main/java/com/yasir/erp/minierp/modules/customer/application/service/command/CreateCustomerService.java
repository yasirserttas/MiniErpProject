package com.yasir.erp.minierp.modules.customer.application.service.command;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerConverter;
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.application.dto.request.CreateCustomerDtoRequest;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.command.CreateCustomerUseCase;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.command.CustomerCommandPort;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerCommandPort customerCommandPort;
    private final UserByIdQueryPort userByIdQueryPort;
    private final CustomerConverter customerConverter;

    public CreateCustomerService(CustomerCommandPort customerCommandPort,
                                 UserByIdQueryPort userByIdQueryPort,
                                 CustomerConverter customerConverter) {
        this.customerCommandPort = customerCommandPort;
        this.userByIdQueryPort = userByIdQueryPort;
        this.customerConverter = customerConverter;
    }

    @Override
    public CustomerDto addCustomer(CreateCustomerDtoRequest req) {
        User user = userByIdQueryPort.findById(req.getUserId())
                .orElseThrow(() -> new UserNotFoundException(req.getUserId()));

        Customer customer = new Customer(
                UUID.randomUUID(),
                req.getName(),
                req.getSurName(),
                req.getCompanyName(),
                req.getPhoneNumber(),
                req.getEmail(),
                req.getTaxNumber(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                user,
                new HashSet<>(),
                new HashSet<>(),
                true
        );

        return customerConverter.convertToCustomer(customerCommandPort.save(customer));
    }
}
