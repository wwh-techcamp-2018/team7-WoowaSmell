package com.woowahan.smell.bazzangee.config;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.woowahan.smell.bazzangee.converter.LocalDateConverter;
import com.woowahan.smell.bazzangee.converter.LocalDateTimeConverter;
import com.woowahan.smell.bazzangee.filter.RequestBodyXSSFileter;
import com.woowahan.smell.bazzangee.interceptor.BasicAuthInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss"));
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

    // Form data
    @Bean
    public FilterRegistrationBean getXssEscapeServletFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/api/reviews", "/chat"); //filter를 거칠 url patterns
        return registrationBean;
    }

    // Request Body
    @Bean
    public FilterRegistrationBean getRequestBodyXSSFIleterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RequestBodyXSSFileter());
        registrationBean.addUrlPatterns("/api/reviews/update", "/chat"); //filter를 거칠 url patterns
        return registrationBean;
    }
}
