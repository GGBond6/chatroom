package cn.upeveryday.chat.pojo;

import lombok.Data;

/**
 * @author ggbond
 */
@Data
public class Permission {
    private Integer id;
    private String name;
    private String path;
    private String comment;
    private String icon;
}
