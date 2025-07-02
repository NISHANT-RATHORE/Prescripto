package com.example.appointmentservice.Configuration;

import com.example.appointmentservice.Service.JwtService;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@Slf4j
public class FeignClientConfig {

    @Autowired
    private JwtService jwtService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Get JWT token from current request
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String token = request.getHeader("Authorization");
                log.info("Received Token: " + token); // Debugging log
                if (token != null && token.startsWith("Bearer ")) {
                    String jwt = token.substring(7).trim();
                    if (jwtService.validateToken(jwt)) {
                        requestTemplate.header("Authorization", token);
                    } else {
                        log.warn("Invalid JWT token. Feign request will not be sent.");
                    }
                } else {
                    log.warn("No valid Authorization header found. Feign request will not be sent.");
                }
            }
        };
    }
}