package com.inssa.server.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class ApiResponse {

    public int statusCode;
    public String responseMessage;


    public void ApiResponse() {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

}
