package com.inssa.server.api.user.dao;

import com.inssa.server.api.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final UserMapper userMapper;
}
