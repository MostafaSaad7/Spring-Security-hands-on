package com.example.springsecurityhandson.config;

import com.example.springsecurityhandson.config.security.filters.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Value("${custom.api.key}")
    private String key;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
return httpSecurity.httpBasic()
        .and().addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class)
        .authorizeRequests().anyRequest().authenticated() // authorization
        //.and().authenticationManager()   or  by adding a bean of type AuthenticationManager
        //.and().authenticationProvider() it doesn't override the AP, it adds one more to the collection
        .and().build();

    }

}
