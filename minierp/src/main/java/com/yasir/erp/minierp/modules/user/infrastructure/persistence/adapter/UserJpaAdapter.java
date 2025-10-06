package com.yasir.erp.minierp.modules.user.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.command.UserCommandPort;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.user.infrastructure.persistence.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserJpaAdapter implements
        UserCommandPort,
        UserByIdQueryPort,
        UserByIdAndActiveQueryPort,
        UserByUsernameQueryPort {

    private final UserJpaRepository userJpaRepository;

    public UserJpaAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByIdAndActive(UUID id, Boolean active) {
        return userJpaRepository.findByIdAndActive(id, active);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }
}