package cn.upeveryday.chat.service;

import cn.upeveryday.chat.pojo.Permission;

import java.util.List;

/**
 * @author ggbond
 */
public interface PermissionService {
    List<Permission> getPermissionsById(Integer userId);
}
