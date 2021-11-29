package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ggbond
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE username=#{username} AND PASSWORD=#{password}")
    User selectByUsernameAndPassword(String username, String password);

    @Select("SELECT * FROM user WHERE username=#{username}")
    User selectByUsername(String username);

    @Insert("INSERT INTO user VALUES (null,#{username},#{password},NULL,NULL,NULL,NULL,NULL)")
    Boolean insertInto(String username,String password);

    @Select("SELECT ur.`role_id` FROM USER JOIN user_role AS ur\n" +
            "ON user.`id`=ur.`user_id` AND user.`id`=#{userId};")
    List<Integer> getRolesIdByUserId(Integer userId);
    @Insert("INSERT INTO user_role VALUES(#{userId},#{roleId});")
    Integer insertRoleAndUser(Integer userId, Integer roleId);
    //默认权限为普通用户
    @Insert("INSERT INTO user_role VALUES(#{userId},default);")
    Integer insertRoleAndUserDefault(Integer userId);
}
