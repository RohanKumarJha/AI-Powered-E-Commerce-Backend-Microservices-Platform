package com.ecommerce.controller;

import com.ecommerce.dto.request.RoleRequest;
import com.ecommerce.dto.response.RoleResponse;
import com.ecommerce.model.enums.RoleType;
import com.ecommerce.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoleService roleService;

    @Test
    void createRoleTest() throws Exception {
        RoleRequest request = new RoleRequest();
        request.setRoleType(RoleType.CUSTOMER);
        request.setDescription("This is role request instance");

        RoleResponse response = new RoleResponse();
        response.setRoleId(1L);
        response.setRoleType(RoleType.CUSTOMER);
        response.setDescription("This is role request instance");
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(roleService.createRole(any(RoleRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                    post("/api/v1/roles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleId").value(1))
                .andExpect(jsonPath("$.roleType").value("CUSTOMER"))
                .andExpect(jsonPath("$.description")
                        .value("This is role request instance"));
    }

    @Test
    void updateRole() throws Exception {

        Long roleId = 1L;

        RoleRequest request = new RoleRequest();
        request.setRoleType(RoleType.CUSTOMER);
        request.setDescription("This is role request instance");

        RoleResponse response = new RoleResponse();
        response.setRoleId(1L);
        response.setRoleType(RoleType.CUSTOMER);
        response.setDescription("This is role request instance");
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(roleService.updateRole(eq(roleId), any(RoleRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                    put("/api/v1/roles/{roleId}", roleId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").value(1))
                .andExpect(jsonPath("$.roleType").value("CUSTOMER"))
                .andExpect(jsonPath("$.description")
                        .value("This is role request instance"));
    }

    @Test
    void deleteRoleTest() throws Exception {

        Long roleId = 1L;

        mockMvc.perform(delete("/api/v1/roles/{roleId}", roleId))
                .andExpect(status().isNoContent());

        verify(roleService).deleteRole(roleId);
    }
}
