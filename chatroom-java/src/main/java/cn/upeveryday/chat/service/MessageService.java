package cn.upeveryday.chat.service;

import cn.upeveryday.chat.pojo.User;

/**
 * @author ggbond
 */
public interface MessageService {
    User getUserByUsername(String username);
}
