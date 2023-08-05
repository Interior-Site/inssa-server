package com.inssa.server.api.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inssa.server.api.review.filter.RequestParamObjectResolver;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestParamObjectResolverTest {

    private RequestParamObjectResolver resolver = new RequestParamObjectResolver(new ObjectMapper());

    @Test
    void singleKeyValueTest() {
        String case1 = resolver.convertQueryStringToJson("K1=V1");
        assertDoesNotThrow(() -> new JSONException(case1));
        assertThat(resolver.isJSONValid(case1)).isTrue();
    }

    @Test
    void multipleKeysWithOneValueTest() {
        String case2 = resolver.convertQueryStringToJson("K1=V1&K2=V2");
        assertDoesNotThrow(() -> new JSONException(case2));
        assertThat(resolver.isJSONValid(case2)).isTrue();
    }

    @Test
    void multipleKeysWithListValueTest() {
        String case3 = resolver.convertQueryStringToJson("K1=V1,V2,V3&K2=V4,V5&K3=V6");
        assertDoesNotThrow(() -> new JSONException(case3));
        assertThat(resolver.isJSONValid(case3)).isTrue();
    }
}