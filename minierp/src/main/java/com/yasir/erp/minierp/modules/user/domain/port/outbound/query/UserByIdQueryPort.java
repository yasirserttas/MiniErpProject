package com.yasir.erp.minierp.modules.user.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.user.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserByIdQueryPort {
    Optional<User> findById(UUID id);
}