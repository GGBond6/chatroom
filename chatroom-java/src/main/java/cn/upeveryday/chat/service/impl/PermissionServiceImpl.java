package cn.upeveryday.chat.service.impl;

import cn.upeveryday.chat.mapper.PermissionMapper;
import cn.upeveryday.chat.pojo.Permission;
import cn.upeveryday.chat.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ggbond
 */
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionsById(Integer userId) {
        return permissionMapper.getPermissionsById(userId);
    }
}
