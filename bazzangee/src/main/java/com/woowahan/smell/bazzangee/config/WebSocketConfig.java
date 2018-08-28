package com.woowahan.smell.bazzangee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahan.smell.bazzangee.interceptor.HttpHandshakeInterceptor;
import com.woowahan.smell.bazzangee.security.HTMLCharacterEscapes;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static com.woowahan.smell.bazzangee.config.ChatRoomName.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/");
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(escapingConverter());
        return true;
    }

    private MessageConverter escapingConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

        MappingJackson2MessageConverter escapingConverter =
                new MappingJackson2MessageConverter();
        escapingConverter.setObjectMapper(objectMapper);

        return escapingConverter;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint(GENERAL.getRoomName())
//                .setHandshakeHandler(new SocketHandshakeHandler())
//                .withSockJS();
        registry.addEndpoint(GENERAL.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(CHICKEN.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(PIZZA.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(WESTERN_FOOD.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(KOREAN_FOOD.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(CHINESE_FOOD.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(JAPANESE_FOOD.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(SNACK_BAR.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
        registry.addEndpoint(HAMBURGER.getRoomName()).addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
    }
}