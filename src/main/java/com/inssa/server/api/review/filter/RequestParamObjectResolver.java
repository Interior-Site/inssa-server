package com.inssa.server.api.review.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class RequestParamObjectResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestParamObject.class) != null;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory
    ) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String json = convertQueryStringToJson(request.getQueryString());
        return objectMapper.readValue(json, parameter.getParameterType());
    }

    public static boolean isJSONValid(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String convertQueryStringToJson(String queryString) {
        StringBuilder sb = new StringBuilder("{");
        String[] pairs = queryString.split("&");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            if (pair.contains("=")) {
                String[] keyValue = pair.split("=");
                sb.append("\"").append(keyValue[0]).append("\"").append(":");
                if (keyValue[1].contains(",")) {
                    sb.append("[");
                    String[] values = keyValue[1].split(",");
                    for (int j = 0; j < values.length; j++) {
                        sb.append("\"").append(values[j]).append("\"");
                        if (j != values.length - 1) sb.append(", ");
                    }
                    sb.append("]");
                } else {
                    sb.append("\"").append(keyValue[1]).append("\"");
                }
                if (i != pairs.length -1) sb.append(",");
            } else {
                throw new InssaException(ErrorCode.INVALID, "요청 파라미터가 올바르지 않습니다.");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
