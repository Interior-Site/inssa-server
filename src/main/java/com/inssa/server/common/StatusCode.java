package com.inssa.server.common;

import lombok.Data;

@Data
public class StatusCode {
	
	/**
	 * 로그인 성공코드
	 */
	public static final int LOGIN_SUCCESS = 200;
	
	/**
	 * 로그인 실패코드
	 */
	public static final int LOGIN_FAIL = 400;

}
