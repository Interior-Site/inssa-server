package com.inssa.server.api.board.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
public class BoardDto {

    public int boardNo;
    public String boardName;
    public String boardGubun;
    public String boardTitle;
    public String boardContent;
    public String boardStatus;
    public Date boardRegDate;
    public Date boardChgDate;
    public int boardView;
    public String boardNotice;
    public int userNo;


}
