package cn.upeveryday.websocket.pojo;

import lombok.Data;

/**
 * @author ggbond
 * 登录响应给浏览器的数据
 */
@Data
public class Result<T> {
    private boolean flag;
    private String message;
    private T data;
}
