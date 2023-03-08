package com.inssa.server.api.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class LikeDto {

    public int likeNo;
    public Date createdDate;

    public int boardNo;

    public String userId;

}
