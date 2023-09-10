package com.inssa.server.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InssaApiResponseBody<T> {
    private final ResponseCode message;
    private final T result;
}
