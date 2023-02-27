package com.inssa.server.api.user.mapper;

import com.inssa.server.api.user.dto.UserChangeInfoRequestDto;
import com.inssa.server.api.user.dto.UserDto;
import com.inssa.server.api.user.dto.UserRegisterRequestDto;
import com.inssa.server.api.user.dto.UserRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByUserId(String userId);

    int register(UserRegisterRequestDto user);

    int existsUserId(String userId);

    int changeUserInfo(UserChangeInfoRequestDto request);

    int changePassword(UserRequestDto request);
}
