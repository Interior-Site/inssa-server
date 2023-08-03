package com.inssa.server.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Schema(description = "API 요청 결과")
@Data
public class InssaApiResponse<T> {
    private final ResponseCoded message;
    private final T result;

    private InssaApiResponse(ResponseCode message, T result) {
        this.message = message;
        this.result = result;
    }

    public static <T> InssaApiResponse<T> ok(@Nullable T result) {
        return new InssaApiResponse<T>(ResponseCode.SUCCESS, result);
    }

    public static <T> InssaApiResponse<T> ok(ResponseCode code) {
        return new InssaApiResponse<T>(code, null);
    }

    public static <T> InssaApiResponse<T> ok(ResponseCode code, @Nullable T result) {
        return new InssaApiResponse<T>(code, result);
    }

    public static <T> InssaApiResponse<T> ok() {
        return new InssaApiResponse<T>(ResponseCode.SUCCESS, null);
    }
}
