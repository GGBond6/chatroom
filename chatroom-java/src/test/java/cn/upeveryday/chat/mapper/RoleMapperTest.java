package cn.upeveryday.chat.mapper;

import cn.upeveryday.chat.ChatApplication;
import cn.upeveryday.chat.pojo.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ggbond
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApplication.class)
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void getRolesById() {
        List<Role> roles = roleMapper.getRolesById(1);
        for (Role role:
             roles) {
            System.out.println(role.getName());
        }
    }
}
