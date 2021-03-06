package cn.upeveryday.chat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.upeveryday.chat.mapper.PermissionMapper;
import cn.upeveryday.chat.mapper.UserMapper;
import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.pojo.User;
import cn.upeveryday.chat.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.query;

/**
 * @author ggbond
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result login(String username, String password) {
        Result result = new Result();
        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (user != null) {
            //根据从数据库获取的userId，再从数据库获取其权限列表
            user.setPermissions(permissionMapper.getPermissionsByUserId(user.getId()));
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
        if (user == null) {
            Boolean bool = userMapper.insertInto(username, password);
            if (bool) {
                result.setFlag(true);
                result.setMessage("注册成功");
            } else {
                result.setFlag(false);
                result.setMessage("注册失败");
            }

        } else {
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
        //用户表插入
        int i = userMapper.insert(user);
        //用户角色表插入（角色ID默认为1，即普通用户）
        Integer j = userMapper.insertRoleAndUserDefault(user.getId());
        if (i != 0 && j != 0) {
            result.setFlag(true);
            result.setMessage("数据保存成功");
        } else {
            result.setFlag(false);
            result.setMessage("数据保存失败");
        }
        return result;
    }

    @Override
    public Result updateById(User user) {
        Result result = new Result();
        int i = userMapper.updateById(user);
        if (i != 0) {
            result.setFlag(true);
            result.setMessage("更新成功");
            result.setData(user);
        } else {
            result.setFlag(false);
            result.setMessage("更新失败");
        }
        return result;
    }

    @Override
    public Result findPage(Integer pageNum, Integer pageSize, String search) {
        Result result = new Result();

        Page<User> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        //当search不为空时，再加入到条件构造器
        if (StrUtil.isNotBlank(search)) {
            wrapper.like("nickname", search);
        }
        wrapper.orderByAsc("id");
        Page<User> res = userMapper.selectPage(userPage, wrapper);
        //res里面有total和records
        if (res != null) {
            //查询出每一个user的角色集合
            List<User> users = res.getRecords();
            for (User user : users) {
                List<Integer> usersId = userMapper.getRolesIdByUserId(user.getId());
                user.setRolesId(usersId);
            }
            result.setFlag(true);
            result.setMessage("查询成功");
            result.setData(res);
        } else {
            result.setFlag(false);
            result.setMessage("查询失败");
        }
        return result;
    }

    @Override
    public Result deleteById(Long id) {
        Result result = new Result();
        int i = userMapper.deleteById(id);
        if (i != 0) {
            result.setFlag(true);
            result.setMessage("删除成功");
        } else {
            result.setFlag(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    @Override
    public Integer insertRoleAndUser(Integer userId, Integer roleId) {
        return userMapper.insertRoleAndUser(userId, roleId);
    }
}
