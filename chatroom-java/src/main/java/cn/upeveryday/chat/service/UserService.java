package cn.upeveryday.chat.service;

import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.pojo.User;

/**
 * @author ggbond
 */
public interface UserService {
    Result login(String username, String password);

    Result register(String username, String password);

    Result selectById(Long id);
    Result insert(User user);
    Result updateById(User user);

    Result findPage(Integer pageNum, Integer pageSize, String search);

    Result deleteById(Long id);

    Integer insertRoleAndUser(Integer userId, Integer roleId);
}
