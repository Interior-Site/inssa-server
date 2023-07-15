package com.inssa.server.common.response;

import lombok.Data;

@Data
public class ResponseMessage {
	
	/**
	 * 성공 코드
	 */
	public static final String SUCCESS = "성공하였습니다.";
	
	/**
	 * 실패 코드
	 */
	public static final String FAIL = "실패하였습니다.";


}
