package com.fithub.model.base;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@RequiredArgsConstructor
public class ResponseModel<T> {
    private boolean success;
    private Object data;
    private String message;

    public ResponseModel(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}
