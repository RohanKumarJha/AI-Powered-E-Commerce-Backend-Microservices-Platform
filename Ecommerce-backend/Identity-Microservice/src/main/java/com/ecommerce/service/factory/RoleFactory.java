package com.ecommerce.service.factory;

import com.ecommerce.dto.request.RoleRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.RoleMapper;
import com.ecommerce.model.Role;
import com.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleFactory {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role createRole(RoleRequest request) {
        return roleMapper.toEntity(request);
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role", "roleId", roleId));
    }
}