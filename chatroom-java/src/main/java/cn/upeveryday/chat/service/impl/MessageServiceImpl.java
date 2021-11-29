package cn.upeveryday.chat.service.impl;

import cn.upeveryday.chat.mapper.MessageMapper;
import cn.upeveryday.chat.pojo.User;
import cn.upeveryday.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ggbond
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public User getUserByUsername(String username){
        return messageMapper.getUserByUsername(username);
    }
}
