package cn.upeveryday.chat.service;

import cn.upeveryday.chat.pojo.Permission;
import cn.upeveryday.chat.pojo.Result;

import java.util.List;

/**
 * @author ggbond
 */
public interface PermissionService {
    List<Permission> getPermissionsByUserId(Integer userId);

    Result insert(Permission permission);

    Result deleteById(Integer id);

    Result update(Permission permission);

    Result getById(Integer id);

    Result findPage(Integer pageNum, Integer pageSize, String search);

    Result findAll();
}
