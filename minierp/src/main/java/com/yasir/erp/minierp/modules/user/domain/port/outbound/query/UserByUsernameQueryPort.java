package com.yasir.erp.minierp.modules.user.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.user.domain.model.User;
import java.util.Optional;

public interface UserByUsernameQueryPort {
    Optional<User> findByUsername(String username);
}