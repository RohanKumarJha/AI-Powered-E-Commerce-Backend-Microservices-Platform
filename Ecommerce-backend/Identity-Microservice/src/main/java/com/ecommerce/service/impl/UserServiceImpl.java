package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.UpdateUserRequest;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.factory.UserFactory;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;
    private final UserFactory userFactory;

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        return null;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return null;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    /*
     * ==========================================================
     * TODO: Phase 3 - Authentication
     * ==========================================================
     *
     * LoginResponse login(LoginRequest request);
     *
     */

}