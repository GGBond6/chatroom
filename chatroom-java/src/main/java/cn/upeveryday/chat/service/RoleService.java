package cn.upeveryday.chat.service;

import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.pojo.Role;
import cn.upeveryday.chat.pojo.User;

import java.util.List;

/**
 * @author ggbond
 */
public interface RoleService {
    List<Role> getRolesByUserId(Integer id);

    Result insert(Role role);

    Result deleteById(Integer id);

    Result update(Role role);

    Result getById(Integer id);

    Result findPage(Integer pageNum, Integer pageSize, String search);

    Integer insertRoleAndPermission(Integer id, Integer id1);
}
