package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RoleRequest;
import com.ecommerce.dto.response.RoleResponse;
import com.ecommerce.factory.RoleFactory;
import com.ecommerce.mapper.RoleMapper;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final RoleMapper roleMapper;
    private final RoleFactory roleFactory;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        return null;
    }

    @Override
    public RoleResponse updateRole(Long roleId, RoleRequest request) {
        return null;
    }

    @Override
    public void deleteRole(Long roleId) {

    }

    @Override
    public RoleResponse assignRole(Long userId, Long roleId) {
        return null;
    }

    @Override
    public RoleResponse removeRole(Long userId, Long roleId) {
        return null;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return List.of();
    }

}