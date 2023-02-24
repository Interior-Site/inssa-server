package com.inssa.server.api.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeDto {

    public int userNo;

    public int boardNo;
    public String like;
}
