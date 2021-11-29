package cn.upeveryday.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author ggbond
 */
@Configuration
public class WebsocketConfig {
    /**
     * 注入ServerEndpointExporter对象，该bean会自动注册使用@ServerEndpoint注解申明的Endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
