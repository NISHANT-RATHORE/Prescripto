package com.example.appointmentservice.Interceptor;

import com.example.appointmentservice.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            System.out.println("Received Token: " + token); // Debugging log
            if (jwtService.validateToken(token)) {
                return true; // Allow the request to proceed
            } else {
                System.out.println("Token validation failed"); // Debugging log
            }
        } else {
            System.out.println("Authorization header is missing or invalid"); // Debugging log
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Invalid or missing JWT");
        return false; // Block the request
    }
}