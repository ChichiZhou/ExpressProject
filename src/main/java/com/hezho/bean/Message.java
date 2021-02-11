package com.hezho.bean;

import org.springframework.stereotype.Component;

@Component
public class Message {
    private String result;

    private Object data;

    private Integer status;

    public Message(){}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Message(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public Message(String result) {
        this.result = result;
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(int status, String result, Object data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }
}
