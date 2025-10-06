package com.yasir.erp.minierp.modules.user.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.user.domain.model.User;

import java.util.UUID;

public interface UserCommandPort {
    User save(User user);
    void delete(User user);
}