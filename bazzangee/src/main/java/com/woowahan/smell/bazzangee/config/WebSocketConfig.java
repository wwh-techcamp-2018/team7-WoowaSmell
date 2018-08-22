package com.woowahan.smell.bazzangee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/smell-general").withSockJS();
        registry.addEndpoint("/smell-chicken").withSockJS();
        registry.addEndpoint("/smell-pizza").withSockJS();
        registry.addEndpoint("/smell-western-food").withSockJS();
        registry.addEndpoint("/smell-korean-food").withSockJS();
        registry.addEndpoint("/smell-chinese-food").withSockJS();
        registry.addEndpoint("/smell-japanese-food").withSockJS();
        registry.addEndpoint("/smell-snack-bar").withSockJS();
        registry.addEndpoint("/smell-hamburger").withSockJS();
    }
}