package com.inssa.server.common.exception;

import com.inssa.server.common.code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class InssaExceptionHandler {

    private static final String CODE = "code";
    private static final String TITLE = "error";

    private static final String MESSAGE = "message";

    @ExceptionHandler(InssaException.class)
    public ResponseEntity<Map<String, Object>> handleServiceException(InssaException exception) {
        int status = ErrorCode.DEFAULT.getCode();
        String cause = ErrorCode.DEFAULT.getMsg();
        if (Objects.nonNull(exception.getErrorCode())) {
            status = exception.getErrorCode().getCode();
            if (Objects.nonNull(exception.getErrorCode().getMsg())) {
                cause = exception.getErrorCode().getMsg();
            }
        }
        return ResponseEntity.status(status)
                .body(Map.of(
                        CODE, status,
                        TITLE, cause,
                        MESSAGE, exception.getMessage()
                ));
    }

    // HTTP Request Parameter error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getFieldErrors().get(0);
        String errorMessage = String.format(
                "%s의 요청 타입이 잘못되었습니다 (입력된 값: %s)",
                fieldError.getField(),
                fieldError.getRejectedValue()
        );
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        CODE, HttpStatus.BAD_REQUEST.value(),
                        TITLE, ErrorCode.INVALID.getMsg(),
                        MESSAGE, errorMessage
                ));
    }

    // HTTP Request Body error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(HttpMessageNotReadableException exception) {
        String errorMessage = exception.getMessage();
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        CODE, HttpStatus.BAD_REQUEST.value(),
                        TITLE, ErrorCode.INVALID.getMsg(),
                        MESSAGE, errorMessage
                ));
    }
}
