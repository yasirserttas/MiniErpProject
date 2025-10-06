package com.yasir.erp.minierp.modules.user.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.user.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserByIdAndActiveQueryPort {
    Optional<User> findByIdAndActive(UUID id, Boolean active);
}