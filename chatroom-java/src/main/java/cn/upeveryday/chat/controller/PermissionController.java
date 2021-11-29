package cn.upeveryday.chat.controller;

import cn.upeveryday.chat.pojo.Permission;
import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @author ggbond
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    //增
    @PostMapping
    public Result insert(@RequestBody Permission permission) {
        return permissionService.insert(permission);
    }

    //删
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        return permissionService.deleteById(id);
    }

    //改
    @PutMapping
    public Result update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    //查
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return permissionService.getById(id);
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
        return permissionService.findPage(pageNum,pageSize,search);
    }

    //查询全部
    @GetMapping("/all")
    public Result findAll(){
        return permissionService.findAll();
    }
}
