package cn.upeveryday.chat.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @author ggbond
 */
@Data
public class User {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer age;
    private String sex;
    private String address;
    private String avatar;

    @TableField(exist = false)//数据库中不存在此字段
    private List<Permission> permissions;
}
