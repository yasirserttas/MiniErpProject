package com.yasir.erp.minierp.modules.user.application.service.query;

import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.user.application.converter.UserConverter;
import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.query.FindUserByIdAndActiveUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.query.FindUserByIdUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdAndActiveQueryPort;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserQueryService implements
        FindUserByIdAndActiveUseCase,
        FindUserByIdUseCase {

    private final UserConverter converter;
    private final UserByIdAndActiveQueryPort byIdAndActivePort;
    private final UserByIdQueryPort byIdPort;

    public UserQueryService(UserConverter converter,
                            UserByIdAndActiveQueryPort byIdAndActivePort,
                            UserByIdQueryPort byIdPort) {
        this.converter = converter;
        this.byIdAndActivePort = byIdAndActivePort;
        this.byIdPort = byIdPort;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserDto findDtoByIdAndActive(UUID userId, Boolean active) {
        return converter.convertToUser(findByIdAndActive(userId,active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserDto findUserDtoById(UUID userId) {
        return converter.convertToUser(findById(userId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected User findById(UUID userId) {
        return byIdPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected User findByIdAndActive(UUID userId, boolean active){

        return byIdAndActivePort.findByIdAndActive(userId,active)
                .orElseThrow(()-> new UserNotFoundException(userId));
    }

}
