package cn.upeveryday.chat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.upeveryday.chat.mapper.PermissionMapper;
import cn.upeveryday.chat.mapper.RoleMapper;
import cn.upeveryday.chat.pojo.*;
import cn.upeveryday.chat.pojo.Role;
import cn.upeveryday.chat.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ggbond
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return roleMapper.getRolesByUserId(userId);
    }

    @Override
    public Result insert(Role role) {
        int i = roleMapper.insert(role);
        if (i!=0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result deleteById(Integer id) {
        int i = roleMapper.deleteById(id);
        if (i!=0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result update(Role role) {
        int i = roleMapper.updateById(role);
        if (i!=0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result getById(Integer id) {
        Role role = roleMapper.selectById(id);
        if (role!=null){
            return Result.success(role);
        }else{
            return Result.error();
        }
    }

    @Override
    public Result findPage(Integer pageNum, Integer pageSize, String search) {
        Page<Role> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(search), Role::getComment, search);
        Page<Role> pages = roleMapper.selectPage(page, wrapper);
        List<Role> records = pages.getRecords();
        if (pages!=null){
            //查出每个角色的权限ID集合
            for (Role role : records) {
                List<Integer> permissionsId = permissionMapper.getPermissionsIdByRoleId(role.getId());
                role.setPermissionsId(permissionsId);
            }
            return Result.success(pages);
        }else{
            return Result.error();
        }
    }

    @Override
    public Integer insertRoleAndPermission(Integer roleId, Integer permissionId) {
        Integer i = roleMapper.insertRoleAndPermission(roleId, permissionId);
        return i;
    }


}
