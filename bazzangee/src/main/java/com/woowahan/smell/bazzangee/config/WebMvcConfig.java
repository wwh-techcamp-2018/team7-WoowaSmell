package com.woowahan.smell.bazzangee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahan.smell.bazzangee.converter.LocalDateConverter;
import com.woowahan.smell.bazzangee.converter.LocalDateTimeConverter;
import com.woowahan.smell.bazzangee.security.BasicAuthInterceptor;
import com.woowahan.smell.bazzangee.security.HTMLCharacterEscapes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    @Bean
    public BasicAuthInterceptor basicAuthInterceptor() {
        return new BasicAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/reviews").setViewName("/review/create");
        registry.addViewController("/reviews/update").setViewName("/review/update");
    }

    @Bean
    public WebMvcConfigurer controlTowerWebConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                super.configureMessageConverters(converters);

                // 5. WebMvcConfigurerAdapter에 MessageConverter 추가
                converters.add(htmlEscapingConveter());
            }

            private HttpMessageConverter<?> htmlEscapingConveter() {
                ObjectMapper objectMapper = new ObjectMapper();
                // 3. ObjectMapper에 특수 문자 처리 기능 적용
                objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

                // 4. MessageConverter에 ObjectMapper 설정
                MappingJackson2HttpMessageConverter htmlEscapingConverter =
                        new MappingJackson2HttpMessageConverter();
                htmlEscapingConverter.setObjectMapper(objectMapper);

                return htmlEscapingConverter;
            }
        };
    }

}
