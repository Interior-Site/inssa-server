package com.inssa.server.common.code;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	/**
	 * CXXX: 공통
	 */
	C000_DEFAULT(0, "테스트용 에러 코드"),

	/**
	 * UXXX: 사용자 관련 에러
	 */
	U000_INVALID_HEADER(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 토큰입니다."),
	U001_UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "로그인 후 이용 가능합니다"),
	U002_INACTIVATED(HttpStatus.FORBIDDEN.value(), "비활성된 계정입니다."), // 신고 먹은 계정
	U003_SLEEPING(HttpStatus.FORBIDDEN.value(), "휴면 계정입니다."), // 미활동 계정
	U004_ALREADY_QUIT(HttpStatus.FORBIDDEN.value(), "이미 탈퇴한 계정입니다."), // 탈퇴 계정

	/**
	 *
	 */


	DUMP(500, "DUMP");

	private int code;
	private String msg;

	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

}
