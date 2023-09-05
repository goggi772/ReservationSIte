package com.reserv.reservationsite.core.config;

import com.reserv.reservationsite.interceptor.TimeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor TimeInterceptor() {
        return new TimeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(TimeInterceptor())
                .addPathPatterns("/bike/reservation");   //예약 버튼을 누르면 시간을 확인함
    }
}
