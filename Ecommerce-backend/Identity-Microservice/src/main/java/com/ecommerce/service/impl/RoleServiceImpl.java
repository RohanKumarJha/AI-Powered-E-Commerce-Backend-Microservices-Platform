package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RoleRequest;
import com.ecommerce.dto.response.RoleResponse;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.service.factory.RoleFactory;
import com.ecommerce.mapper.RoleMapper;
import com.ecommerce.model.Role;
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
    private final RoleMapper roleMapper;
    private final RoleFactory roleFactory;

    private final UserRepository userRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByRoleType(request.getRoleType())) {
            throw new ResourceAlreadyExistsException("Role", "roleType", request.getRoleType());
        }
        Role role = roleFactory.createRole(request);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(Long roleId, RoleRequest request) {
        Role role = roleFactory.getRoleById(roleId);
        if (!role.getRoleType().equals(request.getRoleType())
                && roleRepository.existsByRoleType(request.getRoleType())) {
            throw new ResourceAlreadyExistsException(
                    "Role",
                    "roleType",
                    request.getRoleType());
        }
        role.setRoleType(request.getRoleType());
        role.setDescription(request.getDescription());
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleFactory.getRoleById(roleId);
        roleRepository.delete(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse getRoleById(Long roleId) {
        return roleMapper.toResponse(roleFactory.getRoleById(roleId));
    }

}