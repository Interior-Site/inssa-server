package com.inssa.server.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Swagger schema 인식용 클래스
 */
@Schema(description = "API 요청 결과")
@Getter
@AllArgsConstructor
public class ResponseCodeWrapper {

    @Schema(description = "응답 코드", allowableValues = {"200", "201", "202", "203"})
    private int code;

    @Schema(description = "응답 메시지", allowableValues = {"API 요청 성공", "CREATED", "UPDATED", "DELETED"})
    private String message;
    public static ResponseCodeWrapper from(ResponseCode message) {
        return new ResponseCodeWrapper(message.getCode(), message.getMessage());
    }
}
