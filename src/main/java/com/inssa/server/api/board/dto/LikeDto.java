package com.inssa.server.api.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeDto {

    public String userId;

    public int boardNo;
    public String like;
}
