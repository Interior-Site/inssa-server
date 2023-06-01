package com.inssa.server.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination {

    private int currentPage;
    private int listCount;
    private int boardTypeNo;
    private int limit = 10; // 한 페이지에 보여질 게시글 수
    private int offset = 10; // 보여질 페이지 번호 개수

    private int maxPage;
    private int startPage;
    private int endPage;

    private int prevPage;
    private int nextPage;

    public Pagination() {

    }

    public Pagination paging(int currentPage, int limit) {
        this.currentPage = currentPage;
        this.limit = limit;
        this.offset = limit * (currentPage - 1);

        return this;
    }

    public Pagination(int currentPage, int listCount) {
        this.currentPage = currentPage;
        this.listCount = listCount;

        makePagination();
    }

    public Pagination(int currentPage, int listCount, int boardTypeNo) {
        this.currentPage = currentPage;
        this.listCount = listCount;
        this.boardTypeNo = boardTypeNo;

        makePagination();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;

        makePagination();
    }

    public void setLimit(int limit) {
        this.limit = limit;

        makePagination();
    }

    public void setOffset(int offset) {
        this.offset = offset;

        makePagination();
    }

    private void makePagination() {

        // maxPage == 마지막 페이지 == 총 페이지 수
        maxPage = (int)Math.ceil(   (double)listCount / limit   );

        // startPage == 페이지 번호 목록 시작 번호
        //  ex) 1, 11, 21, 31 .....
        startPage = (currentPage - 1 ) / offset * offset + 1;

        // endPage == 페이지 번호 목록 끝 번호
        // ex) 10, 20, 30, 40 ....
        endPage = startPage + offset - 1;

        // ** 보여지는 페이지 번호 목록의 끝 번호가 maxPage보다 클 경우
        if(endPage > maxPage) {
            endPage = maxPage;
        }

        // 이전, 다음 페이지 번호 목록으로 이동
        if(currentPage < 10) 	prevPage = 1;
        else					prevPage = (currentPage - 1 ) / offset * offset ;

        nextPage = (currentPage + offset - 1) / offset * offset + 1;

        if(nextPage > maxPage) {
            nextPage = maxPage;
        }


    }


}
