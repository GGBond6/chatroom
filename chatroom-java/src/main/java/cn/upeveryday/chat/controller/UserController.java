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

    //插入
    @PostMapping
    public Result save(@RequestBody User user){
        return userService.insert(user);
     }
     //更新
     @PutMapping
     public Result update(@RequestBody User user){
        return userService.updateById(user);
     }
     //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search) {
        return userService.findPage(pageNum,pageSize,search);
    }

    //删除
    @DeleteMapping("/{id}")
    public Result update(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return userService.login(user.getUsername(),user.getPassword());
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user.getUsername(),user.getPassword());
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return userService.selectById(id);
    }


}
