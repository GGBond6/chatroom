package cn.upeveryday.chat.pojo;

import lombok.Data;

/**
 * @author ggbond
 * 统一返回对象Result
 */
@Data
public class Result<T> {
    private boolean flag;
    private String message;
    private T data;

    //成功
    public static Result success() {
        Result result = new Result();
        result.setFlag(true);
        result.setMessage("成功");
        return result;
    }
    public static Result success(String message) {
        Result result = new Result();
        result.setFlag(true);
        result.setMessage(message);
        return result;
    }
    public static <T> Result success(String message, T data) {
        Result result = new Result();
        result.setFlag(true);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static <T> Result success(T data) {
        Result result = new Result();
        result.setFlag(true);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    //失败
    public static Result error() {
        Result result = new Result();
        result.setFlag(false);
        result.setMessage("成功");
        return result;
    }
    public static Result error(String message) {
        Result result = new Result();
        result.setFlag(false);
        result.setMessage(message);
        return result;
    }
    public static <T> Result error(String message, T data) {
        Result result = new Result();
        result.setFlag(false);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static <T> Result error(T data) {
        Result result = new Result();
        result.setFlag(true);
        result.setMessage("失败");
        result.setData(data);
        return result;
    }
}
