package com.example.springsecurityhandson.config.security.AuthManagers;

import com.example.springsecurityhandson.config.security.AuthProviders.ApikeyAuthProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
@AllArgsConstructor
public class ApiKeyAuthManager implements AuthenticationManager {
    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var provider = new ApikeyAuthProvider(key);
        if (provider.supports(authentication.getClass())) {
            return provider.authenticate(authentication);
        }
        return authentication;

    }
}
