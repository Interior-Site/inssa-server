package com.inssa.server.api.user.dao;

import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final UserMapper userMapper;

    public UserDto findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }

    public int register(UserRegisterRequestDto user) {
        return userMapper.register(user);
    }

    public int existsUserId(String userId) {
        return userMapper.existsUserId(userId);
    }

    public int changeUserInfo(UserChangeInfoRequestDto request) {
        return userMapper.changeUserInfo(request);
    }
}
