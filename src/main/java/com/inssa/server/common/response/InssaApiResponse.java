package com.inssa.server.common.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 요청 결과")
public class InssaApiResponse {
    private String code;
    private String message;
    private Object result;

    public InssaApiResponse(String code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static InssaApiResponse ok(Object result) {
        return new InssaApiResponse("OK", "API 요청 성공", result);
    }

    public static InssaApiResponse created(Object result) {
        return new InssaApiResponse("OK", "Object created", result);
    }

    public static InssaApiResponse updated(Object result) {
        return new InssaApiResponse("OK", "Object updated", result);
    }

    public static InssaApiResponse deleted(Object result) {
        return new InssaApiResponse("OK", "Object deleted", result);
    }

    public static InssaApiResponse ok() {
        return new InssaApiResponse("OK", "API 요청 성공", null);
    }
}
