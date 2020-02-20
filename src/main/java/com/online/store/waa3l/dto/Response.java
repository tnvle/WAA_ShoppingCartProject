package com.online.store.waa3l.dto;

import java.io.Serializable;

public class Response implements Serializable {
    private Object data;
    private boolean success = true;
    private ResponseError error;

    public Response(String code, String message, Object data) {
        this.success = false;
        this.error = new ResponseError(code, message, data);
    }

    public Response(Object data) {
        super();
        this.data = data;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }
}
