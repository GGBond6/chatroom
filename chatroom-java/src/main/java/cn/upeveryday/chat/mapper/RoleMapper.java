package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.pojo.Permission;
import cn.upeveryday.chat.pojo.Result;
import cn.upeveryday.chat.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ggbond
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过userID查出用户所有的角色
     * @param userId
     * @return
     */
    @Select("SELECT role.`comment`,role.`id`,role.`name` FROM user_role AS ur JOIN role\n" +
            "ON ur.`role_id`=role.`id`\n" +
            "AND ur.`user_id`=#{userId};")
    List<Role> getRolesByUserId(Integer userId);

    @Insert("INSERT INTO role_permission VALUES(#{roleId},#{permissionId});")
    Integer insertRoleAndPermission(Integer roleId,Integer permissionId);
    @Delete("DELETE FROM user_role WHERE user_id=#{userId};")
    Integer deleteRolesByUserId(Integer userId);
}
