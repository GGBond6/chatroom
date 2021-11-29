package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.pojo.Message;
import cn.upeveryday.chat.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ggbond
 */
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT * FROM user WHERE username=#{username};")
    User getUserByUsername(String username);

}
