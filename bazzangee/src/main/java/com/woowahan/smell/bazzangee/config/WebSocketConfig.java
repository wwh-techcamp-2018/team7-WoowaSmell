package com.woowahan.smell.bazzangee.config;

import com.woowahan.smell.bazzangee.interceptor.HttpHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;

import static com.woowahan.smell.bazzangee.config.ChatRoomName.*;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        Arrays.stream(ChatRoomName.values()).forEach((chatRoom) ->
                registry.addEndpoint(chatRoom.getRoomName())
                        .addInterceptors(new HttpHandshakeInterceptor())
                        .withSockJS());
    }
}