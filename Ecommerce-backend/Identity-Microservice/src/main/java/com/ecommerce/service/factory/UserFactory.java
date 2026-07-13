package com.ecommerce.service.factory;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(RegisterRequest request) {
        return userMapper.toEntity(request);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "userId", userId));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "email", email));
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