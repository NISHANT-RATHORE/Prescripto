package com.example.appointmentservice.Configuration;

import com.example.appointmentservice.Interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**") // Apply to all endpoints
                .addPathPatterns("/appointment/getDoctor")
                .excludePathPatterns("/**"); // Exclude authentication endpoints
    }
}