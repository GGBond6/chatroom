package cn.upeveryday.chat.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
}
