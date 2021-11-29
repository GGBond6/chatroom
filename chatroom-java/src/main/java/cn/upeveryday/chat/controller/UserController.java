package cn.upeveryday.chat.controller;

import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.pojo.User;
import cn.upeveryday.chat.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ggbond
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //增
    @PostMapping
    public Result save(@RequestBody User user) {
        return userService.insert(user);
    }

    //删
    @DeleteMapping("/{id}")
    public Result update(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    //改
    @PutMapping
    public Result update(@RequestBody User user) {
        return userService.updateById(user);
    }

    //查
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return userService.selectById(id);
    }

    //分页查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search) {
        return userService.findPage(pageNum, pageSize, search);
    }


    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user.getUsername(), user.getPassword());
    }


}
