package com.inssa.server.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ApiResponse {

    private int statusCode;
    private String responseMessage;
    private Map<String, Object> data;


    public void ApiResponse() {
        this.statusCode = 0;
        this.responseMessage = "";
        this.data = new HashMap<String, Object>();
    }

    public void ApiResponse(int status, String responseMessage, Map<String, Object> dataMap) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = dataMap;
    }

    public void putData(String key, Object object) {
        this.data.put(key, object);
    }

}
