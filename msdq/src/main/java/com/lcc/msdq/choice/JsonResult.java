package com.lcc.msdq.choice;

public class JsonResult<T> {

    private int code;
    private String message;
    private T result;

    public JsonResult() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return 0 == this.code;
    }

    public String getMsg() {
        return this.message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}