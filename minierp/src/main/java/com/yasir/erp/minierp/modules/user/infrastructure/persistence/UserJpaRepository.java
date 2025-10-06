package com.yasir.erp.minierp.modules.user.infrastructure.persistence;

import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndActive(UUID userId, Boolean active);
    Optional<User> findByUsername(String username);
}