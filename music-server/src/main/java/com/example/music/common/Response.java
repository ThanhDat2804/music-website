package com.example.music.common;

import lombok.Data;

@Data
public class Response {

    private int code;

    private String msg;

    private String type;

    private Boolean success;

    private Object data;

    public static Response success(String msg) {
        Response response = new Response();
        response.setCode(200);
        response.setMsg(msg);
        response.setSuccess(true);
        response.setType("success");
        response.setData(null);
        return response;
    }
    public static Response success(String msg, Object data) {
        Response response = success(msg);
        response.setData(data);
        return response;
    }

    public static Response warning(String msg) {
        Response response = error(msg);
        response.setType("warning");
        return response;
    }

    public static Response error(String msg) {
        Response response = success(msg);
        response.setSuccess(false);
        response.setType("error");
        return response;
    }

    public static Response fatal(String msg) {
        Response response = error(msg);
        response.setCode(500);
        return response;
    }
}
