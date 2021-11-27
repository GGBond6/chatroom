package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ggbond
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 通过userID查出用户拥有的的权限（资源）
     * @param userId
     * @return
     */
    @Select("SELECT p.`comment`,p.`id`,p.`name`,p.`path`,p.`icon`\n" +
            "FROM user_role AS ur JOIN role_permission AS rp JOIN permission AS p\n" +
            "WHERE ur.`role_id`=rp.`role_id` \n" +
            "AND rp.`permission_id`=p.`id`\n" +
            "AND ur.`user_id`=#{userId};")
    List<Permission> getPermissionsById(Integer userId);
}
