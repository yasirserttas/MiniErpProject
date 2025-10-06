package com.yasir.erp.minierp.modules.user.application.service.command;

import com.yasir.erp.minierp.modules.user.application.converter.UserConverter;
import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.application.dto.request.UpdateUserDtoRequest;
import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.UpdateUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.command.UserCommandPort;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService implements UpdateUserUseCase {

    private final UserCommandPort commandPort;
    private final UserByIdQueryPort byIdPort;
    private final UserConverter converter;

    public UpdateUserService(UserCommandPort commandPort,
                             UserByIdQueryPort byIdPort,
                             UserConverter converter) {
        this.commandPort = commandPort;
        this.byIdPort = byIdPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto updateUser(UpdateUserDtoRequest req) {

        User existing = byIdPort.findById(req.getId())
                .orElseThrow(()-> new
                        UserNotFoundException(req.getId()));

        User updated = new User(
                existing.getId(),
                req.getName(),
                req.getSurName(),
                req.getCompanyName(),
                req.getPhoneNumber(),
                req.getUsername(),
                req.getEmail(),
                existing.getPassword(),
                req.getTaxNumber(),
                existing.getCreatedAt(),
                existing.getAddresses(),
                existing.getOrders(),
                existing.getProducts(),
                existing.getCustomers(),
                existing.getInvoices(),
                req.getRoles(),
                true
        );
        return converter.convertToUser(commandPort.save(updated));
    }
}
