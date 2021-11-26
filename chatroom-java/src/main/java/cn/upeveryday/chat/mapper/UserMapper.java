package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

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

}
