package com.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    private HttpStatus statusCode;
    private T response = null;
    private String errorMessage;

    public RestResponse(HttpStatus statusCode, T response) {
        this.statusCode = statusCode;
        this.response = response;
        this.errorMessage = null;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public boolean isOk() {
        return HttpStatus.OK.equals(statusCode) && this.response != null;
    }

    @Nullable
    public T getResponse(){
        if(HttpStatus.OK.equals(statusCode)) {
            return this.response;
        } else {
            return null;
        }
    }

    public static <T> RestResponse<T> errorResponse(Exception e) {
        return new RestResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
    }
}