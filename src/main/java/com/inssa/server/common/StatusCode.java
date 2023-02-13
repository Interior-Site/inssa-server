package com.inssa.server.common;

import lombok.Data;

@Data
public class StatusCode {
	
	/**
	 * 성공코드
	 */
	public static final int SUCCESS = 200;
	
	/**
	 * 실패코드
	 */
	public static final int FAIL = 400;

}
