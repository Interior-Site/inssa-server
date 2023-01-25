package com.inssa.api.common;

import lombok.Data;

@Data
public class ResponseMessage {
	
	/**
	 * 로그인 성공 코드
	 */
	public static final String LOGIN_SUCCESS = "로그인 성공하였습니다.";
	
	/**
	 * 로그인 실패 코드
	 */
	public static final String LOGIN_FAIL = "로그인에 실패하였습니다.";

}
