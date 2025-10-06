package com.yasir.erp.minierp.modules.user.application.service.command;

import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.user.application.converter.UserConverter;
import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.ActivateUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.command.UserCommandPort;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivateUserService implements ActivateUserUseCase {

    private final UserCommandPort commandPort;
    private final UserByIdQueryPort byIdPort;
    private final UserConverter converter;

    public ActivateUserService(UserCommandPort commandPort, UserByIdQueryPort byIdPort, UserConverter converter) {
        this.commandPort = commandPort;
        this.byIdPort = byIdPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto activeUser(UUID userId) {

        User user = byIdPort.findById(userId).
                orElseThrow(()-> new UserNotFoundException(userId));

        User activated = new User(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getCompanyName(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getTaxNumber(),
                user.getCreatedAt(),
                user.getAddresses(),
                user.getOrders(),
                user.getProducts(),
                user.getCustomers(),
                user.getInvoices(),
                user.getRoles(),
                true
        );
        return converter.convertToUser(commandPort.save(activated));
    }
}
