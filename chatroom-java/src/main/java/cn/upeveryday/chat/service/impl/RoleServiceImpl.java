package cn.upeveryday.chat.service.impl;

import cn.upeveryday.chat.mapper.RoleMapper;
import cn.upeveryday.chat.pojo.Role;
import cn.upeveryday.chat.pojo.User;
import cn.upeveryday.chat.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ggbond
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User getRoleById(Integer id) {

        return null;
    }
}
