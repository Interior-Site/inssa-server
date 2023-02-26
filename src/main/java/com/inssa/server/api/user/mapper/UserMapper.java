package com.inssa.server.api.user.mapper;

import com.inssa.server.api.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByEmail(String username);

    int register(UserDto user);
}
