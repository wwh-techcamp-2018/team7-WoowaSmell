package com.woowahan.smell.bazzangee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahan.smell.bazzangee.interceptor.HttpHandshakeInterceptor;
import com.woowahan.smell.bazzangee.security.HTMLCharacterEscapes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;
import java.util.List;

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
        Arrays.stream(ChatRoomName.values()).forEach((chatRoom) ->
                registry.addEndpoint(chatRoom.getRoomName())
                        .addInterceptors(new HttpHandshakeInterceptor())
                        .withSockJS());
    }
}