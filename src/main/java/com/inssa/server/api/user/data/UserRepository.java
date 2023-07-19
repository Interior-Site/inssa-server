package com.inssa.server.api.user.data;

import com.inssa.server.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNo(Long no);
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
