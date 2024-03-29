package com.example.springsecurityhandson.config.security.filters;


import com.example.springsecuritylec4.config.security.AuthManagers.ApiKeyAuthManager;
import com.example.springsecuritylec4.config.security.Authentications.ApiKeyAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiKeyAuthManager apiKeyAuthManager = new ApiKeyAuthManager(key);
        var requestKey = request.getHeader("x-api-key");

        if (requestKey == null || "null".equals(requestKey)) {
            filterChain.doFilter(request, response);
        }

        var auth = new ApiKeyAuthentication(requestKey);


        try {
            var a = apiKeyAuthManager.authenticate(auth);
            if (a.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(a);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
