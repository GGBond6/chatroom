package cn.upeveryday.chat.service.impl;

import cn.upeveryday.chat.mapper.PermissionMapper;
import cn.upeveryday.chat.pojo.Permission;
import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ggbond
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionsByUserId(Integer userId) {
        return permissionMapper.getPermissionsByUserId(userId);
    }

    @Override
    public Result insert(Permission permission) {
        int i = permissionMapper.insert(permission);
        if (i!=0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result deleteById(Integer id) {
        int i = permissionMapper.deleteById(id);
        if (i!=0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result update(Permission permission) {
        int i = permissionMapper.updateById(permission);
        if (i!=0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result getById(Integer id) {
        Permission permission = permissionMapper.selectById(id);
        if (permission!=null){
            return Result.success(permission);
        }else{
            return Result.error();
        }
    }

    @Override
    public Result findPage(Integer pageNum, Integer pageSize, String search) {
        Page<Permission> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Permission> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(search), Permission::getComment, search);
        Page<Permission> pages = permissionMapper.selectPage(page, wrapper);
        if (pages!=null){
            return Result.success(pages);
        }else {
            return Result.error();
        }
    }

    @Override
    public Result findAll() {
        List<Permission> permissions = permissionMapper.selectList(null);
        if (permissions!=null){
            return Result.success(permissions);
        }else {
            return Result.error();
        }
    }
}
