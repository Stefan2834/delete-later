package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.NonNull;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

        System.out.println(generate());
        if (!isValidJwt(token)) {
            Response<String> res = new Response<>(false, "Invalid JWT");
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(jsonMapper.writeValueAsString(res));
            return false;
        }

        return true;
    }


    private static final String SECRET_KEY = "yourSecretKey12345678901234567890";
    private static final int HS256_KEY_SIZE = 256;
    public boolean isValidJwt(String token) {
        try {
            byte[] secretKeyBytes = SECRET_KEY.getBytes();
            byte[] truncatedKey = new byte[HS256_KEY_SIZE / 8];
            System.arraycopy(secretKeyBytes, 0, truncatedKey, 0, truncatedKey.length);

            Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(truncatedKey)).build()
                    .parseClaimsJws(token).getBody();
            System.out.println("Name: " + claims.get("name"));
            System.out.println("Username: " + claims.get("username"));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generate() {
        long expirationMillis = 360000000;
        byte[] secretKeyBytes = SECRET_KEY.getBytes();
        byte[] truncatedKey = new byte[HS256_KEY_SIZE / 8];
        System.arraycopy(secretKeyBytes, 0, truncatedKey, 0, truncatedKey.length);

        return Jwts.builder()
                .setSubject("user")
                .claim("name", "John Doe")
                .claim("username", "john.doe")
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationMillis))
                .signWith(Keys.hmacShaKeyFor(truncatedKey), SignatureAlgorithm.HS256)
                .compact();
    }

    record Response<T>(boolean success, T data) {
    }
}
