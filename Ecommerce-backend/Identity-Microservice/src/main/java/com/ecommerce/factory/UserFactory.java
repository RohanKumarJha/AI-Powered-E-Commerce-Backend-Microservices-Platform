package com.ecommerce.factory;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserMapper userMapper;

    public User createUser(RegisterRequest request) {
        return userMapper.toEntity(request);
    }

    /*
     * ==========================================================
     * TODO: Phase 3 - OAuth2 / Social Login
     * ==========================================================
     *
     * Methods:
     * - createOAuthUser(...)
     * - createGoogleUser(...)
     * - createGithubUser(...)
     *
     * Responsibilities:
     * - Create user from OAuth provider
     * - Assign default CUSTOMER role
     * - Mark account as verified
     * - Store provider details
     */
}