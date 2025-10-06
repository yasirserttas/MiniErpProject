package com.yasir.erp.minierp.modules.user.application.service.command;

import com.yasir.erp.minierp.modules.user.application.converter.UserConverter;
import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.DeactivateUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.command.UserCommandPort;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateUserService implements DeactivateUserUseCase {

    private final UserCommandPort commandPort;
    private final UserByIdQueryPort byIdPort;
    private final UserConverter converter;

    public DeactivateUserService(UserCommandPort commandPort, UserByIdQueryPort byIdPort, UserConverter converter) {
        this.commandPort = commandPort;
        this.byIdPort = byIdPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto reactiveUser(UUID userId) {

        User user = byIdPort.findById(userId).
                orElseThrow();

        User deactivated = new User(
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
                false
        );
        return converter.convertToUser(commandPort.save(deactivated));
    }
}
