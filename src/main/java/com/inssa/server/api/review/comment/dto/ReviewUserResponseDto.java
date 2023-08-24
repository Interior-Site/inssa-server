package com.inssa.server.api.review.comment.dto;

import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.user.model.UserRole;
import com.inssa.server.share.user.UserStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewUserResponseDto {
    private final Long no;
    private final String nickname;
    private final UserStatus status;
    private final ImageResponseDto profile;
    private final UserRole role;
}
