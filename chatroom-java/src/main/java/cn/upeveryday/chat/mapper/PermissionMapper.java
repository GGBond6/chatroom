package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PutMapping;

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
    @Select("SELECT DISTINCT p.*\n" +
            "FROM user_role AS ur  JOIN role_permission AS rp  JOIN permission AS p\n" +
            "WHERE ur.`role_id`=rp.`role_id` \n" +
            "AND rp.`permission_id`=p.`id`\n" +
            "AND ur.`user_id`=#{userId};")
    List<Permission> getPermissionsByUserId(Integer userId);

    @Select("SELECT p.id FROM role_permission AS rp JOIN permission AS p\n" +
            "ON rp.`permission_id`=p.`id` AND rp.`role_id`=#{roleId};")
    List<Integer> getPermissionsIdByRoleId(Integer roleId);

    @Delete("DELETE FROM role_permission WHERE role_permission.`role_id`=#{roleId};")
    Integer deletePermissionsByRoleId(Integer roleId);
}
