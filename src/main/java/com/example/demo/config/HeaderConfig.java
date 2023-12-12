package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;



public class HeaderConfig implements HandlerInterceptor {

    private final ObjectMapper jsonMapper = new ObjectMapper();


    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            Response<String> res = new Response<>(false, "Access token not found");
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(jsonMapper.writeValueAsString(res));
            return false;
        }

        String token = authorizationHeader.substring("Bearer ".length());


        return true;
    }


    record Response<T>(boolean success, T data) {
    }
}
