package com.aidar.tuneflow.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}