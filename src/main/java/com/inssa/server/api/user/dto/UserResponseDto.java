package com.inssa.server.api.user.dto;

import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.user.model.User;
import com.inssa.server.api.user.model.UserRole;
import com.inssa.server.share.user.UserStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserResponseDto {
    private final Long no;
    private final String nickname;
    private final String email;
    private final UserStatus status;
    private final ImageResponseDto profile;
    private final UserRole role;

    public UserResponseDto(User user){
        this.no = user.getNo();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.profile = new ImageResponseDto(user.getProfile());
        this.role = user.getRole();
    }
}
