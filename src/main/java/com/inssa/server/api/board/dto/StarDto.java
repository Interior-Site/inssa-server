package com.inssa.server.api.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StarDto {

    public int boardNo;

    public String userId;

    public int star;

    public String boardGubun;
}
