package cn.upeveryday.chat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.upeveryday.chat.mapper.UserMapper;
import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.pojo.User;
import cn.upeveryday.chat.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ggbond
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(String username, String password) {
        Result result = new Result();
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("username", username);
//        wrapper.eq("password", password);
//        User user = userMapper.selectOne(wrapper);
        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (user != null) {
            result.setFlag(true);
            result.setMessage("登录成功");
            result.setData(user);
        } else {
            result.setFlag(false);
            result.setMessage("用户名或密码错误");
        }
        return result;
    }

    @Override
    public Result register(String username, String password) {
        Result result = new Result();
        User user = userMapper.selectByUsername(username);
        if (user==null){
            Boolean bool = userMapper.insertInto(username, password);
            if (bool){
                result.setFlag(true);
                result.setMessage("注册成功");
            }else {
                result.setFlag(false);
                result.setMessage("注册失败");
            }

        }else{
            result.setFlag(false);
            result.setMessage("此用户名已存在");
        }
        return result;
    }

    @Override
    public Result selectById(Long id) {
        Result result = new Result();
        User user = userMapper.selectById(id);
        result.setFlag(true);
        result.setData(user);
        return result;
    }

    @Override
    public Result insert(User user) {
        Result result = new Result();
        int i = userMapper.insert(user);
        if (i!=0){
            result.setFlag(true);
            result.setMessage("数据保存成功");
        }else {
            result.setFlag(false);
            result.setMessage("数据保存失败");
        }
        return result;
    }

    @Override
    public Result updateById(User user) {
        Result result = new Result();
        int i = userMapper.updateById(user);
        if (i!=0){
            result.setFlag(true);
            result.setMessage("更新成功");
            result.setData(user);
        }else {
            result.setFlag(false);
            result.setMessage("更新失败");
        }
        return result;
    }

    @Override
    public Result findPage(Integer pageNum, Integer pageSize, String search) {
        Result result = new Result();

        //分页查询：封装page对象，根据page对象和条件查询；得到的还是page对象，调用getRecords获取数据
        Page<User>  userPage=new Page<>(pageNum, pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        //当search不为空时，再加入到条件构造器
        if (StrUtil.isNotBlank(search)) {
            wrapper.like("nickname", search);
        }
        wrapper.orderByAsc("id");
        Page<User> res = userMapper.selectPage(userPage, wrapper);
        //res里面有total和records
        if (res!=null){
            result.setFlag(true);
            result.setMessage("查询成功");
            result.setData(res);
        }else {
            result.setFlag(false);
            result.setMessage("查询失败");
        }
        return result;
    }

    @Override
    public Result deleteById(Long id) {
        Result result = new Result();
        int i = userMapper.deleteById(id);
        if (i!=0){
            result.setFlag(true);
            result.setMessage("删除成功");
        }else {
            result.setFlag(false);
            result.setMessage("删除失败");
        }
        return result;
    }
}
