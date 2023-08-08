package com.inssa.server.common.exception;

import com.inssa.server.common.code.ErrorCode;
import org.hibernate.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class InssaExceptionHandler {

    private static final String TITLE = "error";
    private static final String CONTENT = "message";

    @ExceptionHandler(InssaException.class)
    public ResponseEntity<Map<String, String>> handleServiceException(InssaException exception) {
        int status = ErrorCode.DEFAULT.getCode();
        String cause = ErrorCode.DEFAULT.getMsg();
        if (Objects.nonNull(exception.getErrorCode())) {
            status = exception.getErrorCode().getCode();
            if (Objects.nonNull(exception.getErrorCode().getMsg())) {
                cause = exception.getErrorCode().getMsg();
            }
        }
        return ResponseEntity.status(status)
                .body(Map.of(TITLE, cause, CONTENT, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(Map.of(TITLE, ErrorCode.INVALID.getMsg(), CONTENT, errorMessage));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(TypeMismatchException exception) {
        String errorMessage = exception.getMessage();
        return ResponseEntity
                .badRequest()
                .body(Map.of(TITLE, ErrorCode.INVALID.getMsg(), CONTENT, errorMessage));
    }
}
