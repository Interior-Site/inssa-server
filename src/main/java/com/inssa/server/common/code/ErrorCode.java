package com.inssa.server.common.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID(400, "INVALID"),
	UNAUTHORIZED(401, "UNAUTHORIZED"),
	FORBIDDEN(403, "FORBIDDEN"),
	NOT_FOUND(404, "NOT FOUND"),
	CONFLICT(409, "CONFLICT"),
	INTERNAL_SERVER_ERROR(500, "API 요청 실패"),
	DEFAULT(600, "테스트용 에러 코드")
	;

	private final int code;
	private final String msg;
	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
