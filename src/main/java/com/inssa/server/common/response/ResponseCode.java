package com.inssa.server.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "API 요청 성공"),
    CREATED(201, "CREATED"),
    UPDATED(202, "UPDATED"),
    DELETED(203, "DELETED")
    ;

    private final int code;
    private final String message;
}
