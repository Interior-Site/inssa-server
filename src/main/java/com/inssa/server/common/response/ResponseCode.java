package com.inssa.server.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ResponseCode {
    SUCCESS(200, "API 요청 성공"),
    CREATED(201, "CREATED"),
    UPDATED(202, "UPDATED"),
    DELETED(203, "DELETED")
    ;


    @Schema(description = "API 응답 코드")
    private final int code;

    @Schema(description = "API 응답 메시지")
    private final String message;
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
