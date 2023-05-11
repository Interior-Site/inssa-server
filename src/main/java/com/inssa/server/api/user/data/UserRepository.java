package com.inssa.server.api.user.data;

import com.inssa.server.api.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
