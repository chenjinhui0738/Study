package com.cjh.websocket.config;

import com.cjh.websocket.handler.MyStringWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket//开启websocket
public class MyWebSocketServerConfigure implements WebSocketConfigurer {
    @Autowired
    private MyStringWebSocketHandler myStringWebSocketHandler;

    /**
     * 注册处理类
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //当客户端通过/connect url和服务端连接通信时，使用MyStringWebSocketHandler处理会话
        registry.addHandler(myStringWebSocketHandler, "/connect").setAllowedOrigins("*").withSockJS();
    }
}
