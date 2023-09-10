package com.inssa.server.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

@Schema(description = "API 요청 결과")
@Data
public class InssaApiResponse<T> extends ResponseEntity<InssaApiResponseBody<T>> {

    private InssaApiResponse(ResponseCode message, T result) {
        super(new InssaApiResponseBody<>(message, result), message.getHttpStatus());
    }

    private InssaApiResponse(ResponseCode message, MultiValueMap<String, String> headers, T result) {
        super(new InssaApiResponseBody<>(message, result), headers, message.getHttpStatus());
    }

    public static <T> InssaApiResponse<T> success(T result) {
        return new InssaApiResponse<>(ResponseCode.SUCCESS, result);
    }

    public static <T> InssaApiResponse<T> success(ResponseCode code) {
        return new InssaApiResponse<>(code, null);
    }

    public static <T> InssaApiResponse<T> success(ResponseCode code, @Nullable T result) {
        return new InssaApiResponse<>(code, result);
    }

    public static <T> InssaApiResponse<T> success(
            ResponseCode code,
            @Nullable MultiValueMap<String, String> headers,
            @Nullable T result
    ) {
        return new InssaApiResponse<>(code, headers, result);
    }
}


