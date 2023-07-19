package com.inssa.server.common.code;

import lombok.Data;

public enum ErrorCode {
	DEFAULT(600, "테스트용 에러 코드");

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
