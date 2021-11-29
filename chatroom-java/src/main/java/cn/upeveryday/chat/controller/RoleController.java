package cn.upeveryday.chat.controller;

import cn.upeveryday.chat.mapper.PermissionMapper;
import cn.upeveryday.chat.pojo.Permission;
import cn.upeveryday.chat.pojo.Role;
import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ggbond
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionMapper permissionMapper;
    //增
    @PostMapping
    public Result insert(@RequestBody Role role) {
        return roleService.insert(role);
    }

    //删
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        return roleService.deleteById(id);
    }

    //改
    @PutMapping
    public Result update(@RequestBody Role role) {
        return roleService.update(role);
    }

    //查
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    /**
     * 分页查询
     * @param pageNum:当前页数
     * @param pageSize:每一页的大小
     * @param search:要搜索的内容
     * @return Result
     */
    @GetMapping
    public Result findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        return roleService.findPage(pageNum,pageSize,search);
    }

    //改变当前角色的权限
    @PutMapping("/changePermission")
    public Result changePermission(@RequestBody Role role){
        try{
            //先删除已有的权限
            Integer i = permissionMapper.deletePermissionsByRoleId(role.getId());
            //再插入新的权限
            for (Integer permissionId : role.getPermissionsId()) {
                roleService.insertRoleAndPermission(role.getId(), permissionId);
            }
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 返回当前用户的角色集合
     * @param userId
     * @return
     */
    @GetMapping("/getByUserId")
    Result getRolesByUserId(@RequestParam Integer userId){
        List<Role> roles = roleService.getRolesByUserId(userId);
        if (roles!=null){
            return Result.success(roles);
        }else{
            return Result.error();
        }
    }

    //查询全部
    @GetMapping("/all")
    public Result findAll(){
        return roleService.findAll();
    }
}
