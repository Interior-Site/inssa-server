package com.inssa.server.common.exception;

import com.inssa.server.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class InssaException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String msg;

    public InssaException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public InssaException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.msg = errorCode.getMsg();
    }

    public InssaException(String msg) {
        super(msg);
        this.errorCode = null;
        this.msg = msg;
    }
}
