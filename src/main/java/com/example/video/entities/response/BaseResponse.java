package com.example.video.entities.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse implements Serializable {

    private Object body;
    private String message;
    private Long totalRecord;

    public BaseResponse() {
        this.message = "succecss";
    }

    public BaseResponse(String message) {
        this.message = message;
    }

    public BaseResponse(Object body, String message) {
        this.message = message;
        this.body = body;
    }

    public BaseResponse(Object body) {
        this.message = "succecss";
        this.body = body;
    }

    public BaseResponse(Object body, long totalRecord, String message) {
        this.message = message;
        this.body = body;
        this.totalRecord = totalRecord;
    }

    public BaseResponse(Object body, long totalRecord) {
        this.message = "succecss";
        this.body = body;
        this.totalRecord = totalRecord;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
