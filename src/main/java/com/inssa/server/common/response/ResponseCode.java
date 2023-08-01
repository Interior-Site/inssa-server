package com.inssa.server.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ResponseCode {
    SUCCESS(200, "API 요청 성공"),
    CREATED(201, "CREATED"),
    UPDATED(202, "UPDATED"),
    DELETED(203, "DELETED")
    ;

    private final int code;
    private final String message;
    private ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
