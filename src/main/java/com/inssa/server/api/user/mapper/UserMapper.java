package com.inssa.server.api.user.mapper;

import com.inssa.server.api.user.dto.UserDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByUserId(String userId);

    int register(UserRegisterRequestDto user);
}
