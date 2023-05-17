﻿DROP TABLE IF EXISTS COMPANY;

CREATE TABLE COMPANY (
	COMPANY_NO		INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '업체고유번호',
	REGISTRATION_NO	VARCHAR(100)	NOT NULL								COMMENT '사업자등록번호',
	COMPANY_NAME	VARCHAR(100)	NOT NULL								COMMENT '업체명',
	CONTACT_NUMBER	VARCHAR(100)	NOT NULL								COMMENT '업체전화번호',
	STATUS			VARCHAR(50)		NOT NULL	DEFAULT 'Y'					COMMENT '운영: Y / 삭제: N',
	APPROVAL		CHAR(1)			NOT NULL	DEFAULT 'N'					COMMENT '승인: Y / 미승인: N',
	RATING			CHAR(1)				NULL								COMMENT '평점',
	USER_ID			VARCHAR(50)		NOT NULL								COMMENT '업체 아이디',
	CONSTRAINT FK_USER_TO_COMPANY FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID)
);

DROP TABLE IF EXISTS BOARD_TYPE;

CREATE TABLE BOARD_TYPE (
	BOARD_TYPE_NO		INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '게시판 구분 번호',
	BOARD_TYPE_NAME		VARCHAR(50)		NOT NULL								COMMENT '게시판 구분명 ex) 후기, 문의, 소통'
);

DROP TABLE IF EXISTS CATEGORY;

CREATE TABLE CATEGORY (
	CATEGORY_NO			INT			NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '카테고리 번호',
	PARENT_CATEGORY		VARCHAR(50)		NULL								COMMENT '상위 카테고리 ex) 집 분류, 시공 분류, 가격 ...', -- 후기 게시판에만 적용됨
	CATEGORY_NAME		VARCHAR(50)	NOT NULL								COMMENT '카테고리 이름 ex) 아파트, 시공 후기, 베란다 확장 ...',
	BOARD_TYPE_NO		INT			NOT NULL								COMMENT '게시판 구분 번호',
	CONSTRAINT FK_BOARD_TYPE_TO_CATEGORY FOREIGN KEY (BOARD_TYPE_NO) REFERENCES BOARD_TYPE (BOARD_TYPE_NO)
);

DROP TABLE IF EXISTS BOARD;

CREATE TABLE BOARD (
	BOARD_NO		INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '게시글번호',
	BOARD_TYPE_NO	INT				NOT NULL								COMMENT '게시판 구분 번호',
	CATEGORY_NO		INT				NOT NULL								COMMENT '카테고리 번호',
	PARENT_BOARD_NO	INT 				NULL								COMMENT '부모 게시글 번호',
	TITLE			VARCHAR(100)	NOT NULL	DEFAULT 'Y'					COMMENT '게시글 제목',
	CONTENT			LONGTEXT		NOT NULL								COMMENT '게시글 내용',
	CREATED_DATE	DATE			NOT NULL	DEFAULT SYSDATE()			COMMENT '게시글 작성일',
	MODIFIED_DATE	DATE				NULL								COMMENT '게시글 수정일',
	DELETE_AT		BOOLEAN			NOT NULL	DEFAULT 0					COMMENT '삭제 여부 (0: false, 1: true)',
	VIEW_COUNT		INT				NOT NULL	DEFAULT 0					COMMENT '조회수',
	LIKE_COUNT		INT 			NOT NULL	DEFAULT 0					COMMENT '좋아요 수',
	NOTICE_AT		BOOLEAN			NOT NULL	DEFAULT 0					COMMENT '공지사항 여부 (0: false, 1: true)',
	USER_ID			VARCHAR(50)		NOT NULL								COMMENT '작성자 아이디',
	CONSTRAINT FK_USER_TO_BOARD FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID),
	CONSTRAINT FK_BOARD_TYPE_TO_BOARD FOREIGN KEY (BOARD_TYPE_NO) REFERENCES BOARD_TYPE (BOARD_TYPE_NO),
	CONSTRAINT FK_CATEGORY_TO_BOARD FOREIGN KEY (CATEGORY_NO) REFERENCES CATEGORY (CATEGORY_NO)
);

DROP TABLE IF EXISTS COMMENT;

CREATE TABLE COMMENT (
	COMMENT_NO			INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '댓글 번호',
	PARENT_COMMENT_NO	INT				    NULL								COMMENT '부모 댓글 번호',
	CONTENT				VARCHAR(1000)	NOT NULL								COMMENT '댓글 내용',
	DELETE_AT			BOOLEAN			NOT NULL	DEFAULT 0					COMMENT '삭제 여부 (0: false, 1: true)',
	CREATED_DATE		DATE			NOT NULL	DEFAULT SYSDATE()			COMMENT '댓글 작성일',
	MODIFIED_DATE		DATE				NULL								COMMENT '댓글 수정일',
	BOARD_NO			INT				NOT NULL								COMMENT '댓글이 달린 게시글번호',
	USER_ID				VARCHAR(50)		NOT NULL								COMMENT '작성자 아이디',
	CONSTRAINT FK_BOARD_TO_COMMENT FOREIGN KEY (BOARD_NO) REFERENCES BOARD (BOARD_NO),
	CONSTRAINT FK_USER_TO_COMMENT FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID)
);

DROP TABLE IF EXISTS BOARD_LIKE;

CREATE TABLE BOARD_LIKE (
	LIKE_NO			INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '좋아요 번호',
	CREATED_DATE	DATE			NOT NULL	DEFAULT SYSDATE()			COMMENT '좋아요 날짜',
	BOARD_NO		INT				NOT NULL								COMMENT '좋아요가 눌린 게시글 번호',
	USER_ID			VARCHAR(50)		NOT NULL								COMMENT '좋아요를 누른 회원 아이디',
	CONSTRAINT FK_BOARD_TO_BOARD_LIKE FOREIGN KEY (BOARD_NO) REFERENCES BOARD (BOARD_NO),
	CONSTRAINT FK_USER_TO_BOARD_LIKE FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID)
);

DROP TABLE IF EXISTS BOOKMARK;

CREATE TABLE BOOKMARK (
	BOOKMARK_NO		INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '게시글 찜번호',
	CREATED_DATE	DATE			NOT NULL	DEFAULT SYSDATE()			COMMENT '찜하기 눌린 날짜',
	BOARD_NO		INT				NOT NULL								COMMENT '찜하기 눌린 게시글 번호',
	USER_ID			VARCHAR(50)		NOT NULL								COMMENT '찜하기 누른 회원 아이디',
	CONSTRAINT FK_BOARD_TO_BOOKMARK FOREIGN KEY (BOARD_NO) REFERENCES BOARD (BOARD_NO),
	CONSTRAINT FK_USER_TO_BOOKMARK FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID)
);


DROP TABLE IF EXISTS FILE;

CREATE TABLE FILE (
	FILE_NO			INT				NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '파일번호',
	FILE_PATH		VARCHAR(500)	NOT NULL								COMMENT '파일저장경로',
	FILE_TYPE		VARCHAR(100)	NOT NULL 								COMMENT '파일 타입',
	CHANGE_NAME		VARCHAR(200)	NOT NULL								COMMENT '파일서버에 저장될 파일명',
	ORIGIN_NAME		VARCHAR(200)	NOT NULL								COMMENT '원본파일명',
	CREATED_DATE	DATE			NOT NULL	DEFAULT SYSDATE()			COMMENT '파일등록일',
	BOARD_NO		INT				NOT NULL								COMMENT '게시글번호',
	CONSTRAINT FK_BOARD_TO_FILE FOREIGN KEY (BOARD_NO) REFERENCES BOARD (BOARD_NO)
);
