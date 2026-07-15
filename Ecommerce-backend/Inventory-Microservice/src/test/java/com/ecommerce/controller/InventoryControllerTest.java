package com.ecommerce.controller;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.dto.request.UpdateInventoryRequest;
import com.ecommerce.dto.request.UpdateStockRequest;
import com.ecommerce.dto.response.InventoryResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryService inventoryService;

    @Test
    void createInventoryTest() throws Exception {
        InventoryRequest request = new InventoryRequest();
        request.setProductId(1L);
        request.setSku("SKU-101");
        request.setAvailableQuantity(100);
        request.setMinimumStockLevel(10);

        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(1L);
        response.setProductId(1L);
        response.setSku("SKU-101");
        response.setAvailableQuantity(100);
        response.setMinimumStockLevel(10);
        response.setReservedQuantity(10);
        response.setSoldQuantity(5);
        response.setActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(inventoryService.createInventory(any(InventoryRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.sku").value("SKU-101"))
                .andExpect(jsonPath("$.availableQuantity").value(100))
                .andExpect(jsonPath("$.reservedQuantity").value(10))
                .andExpect(jsonPath("$.soldQuantity").value(5))
                .andExpect(jsonPath("$.minimumStockLevel").value(10))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void getInventoryByProductIdTest() throws Exception {
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(1L);
        response.setProductId(101L);
        response.setSku("SKU-101");
        response.setAvailableQuantity(100);
        response.setMinimumStockLevel(10);
        response.setReservedQuantity(10);
        response.setSoldQuantity(5);
        response.setActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(inventoryService.getInventoryByProductId(101L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/inventories/product/101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.productId").value(101L))
                .andExpect(jsonPath("$.sku").value("SKU-101"))
                .andExpect(jsonPath("$.availableQuantity").value(100))
                .andExpect(jsonPath("$.reservedQuantity").value(10))
                .andExpect(jsonPath("$.soldQuantity").value(5))
                .andExpect(jsonPath("$.minimumStockLevel").value(10))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void getInventoryByIdTest() throws Exception {
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(1L);
        response.setProductId(101L);
        response.setSku("SKU-101");
        response.setAvailableQuantity(100);
        response.setReservedQuantity(10);
        response.setSoldQuantity(5);
        response.setMinimumStockLevel(10);
        response.setActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(inventoryService.getInventoryById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/inventories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.productId").value(101))
                .andExpect(jsonPath("$.sku").value("SKU-101"))
                .andExpect(jsonPath("$.availableQuantity").value(100))
                .andExpect(jsonPath("$.reservedQuantity").value(10))
                .andExpect(jsonPath("$.soldQuantity").value(5))
                .andExpect(jsonPath("$.minimumStockLevel").value(10))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());


    }

    @Test
    void getAllInventoriesTest() throws Exception {
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(1L);
        response.setProductId(101L);
        response.setSku("SKU-101");
        response.setAvailableQuantity(100);
        response.setReservedQuantity(10);
        response.setSoldQuantity(5);
        response.setMinimumStockLevel(10);
        response.setActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        PageResponse<InventoryResponse> pageResponse = PageResponse.<InventoryResponse>builder()
                .content(List.of(response))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .first(true)
                .last(true)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(inventoryService.getAllInventories(
                any(),
                any(),
                any(),
                any()))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/inventories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].inventoryId").value(1))
                .andExpect(jsonPath("$.content[0].productId").value(101))
                .andExpect(jsonPath("$.content[0].sku").value("SKU-101"))
                .andExpect(jsonPath("$.content[0].availableQuantity").value(100))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(false));
    }

    @Test
    void updateInventoryTest() throws Exception {

        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setSku("SKU-101");
        request.setMinimumStockLevel(15);
        request.setActive(true);

        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(1L);
        response.setProductId(101L);
        response.setSku("SKU-101");
        response.setAvailableQuantity(100);
        response.setReservedQuantity(10);
        response.setSoldQuantity(5);
        response.setMinimumStockLevel(15);
        response.setActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(inventoryService.updateInventory(
                any(Long.class),
                any(UpdateInventoryRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/inventories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.productId").value(101))
                .andExpect(jsonPath("$.sku").value("SKU-101"))
                .andExpect(jsonPath("$.availableQuantity").value(100))
                .andExpect(jsonPath("$.reservedQuantity").value(10))
                .andExpect(jsonPath("$.soldQuantity").value(5))
                .andExpect(jsonPath("$.minimumStockLevel").value(15))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateStockTest() throws Exception {

        UpdateStockRequest request = new UpdateStockRequest();
        request.setQuantity(150);

        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(1L);
        response.setProductId(101L);
        response.setSku("SKU-101");
        response.setAvailableQuantity(150);
        response.setReservedQuantity(10);
        response.setSoldQuantity(5);
        response.setMinimumStockLevel(10);
        response.setActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(inventoryService.updateStock(
                any(Long.class),
                any(UpdateStockRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(patch("/api/v1/inventories/1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.productId").value(101))
                .andExpect(jsonPath("$.sku").value("SKU-101"))
                .andExpect(jsonPath("$.availableQuantity").value(150))
                .andExpect(jsonPath("$.reservedQuantity").value(10))
                .andExpect(jsonPath("$.soldQuantity").value(5))
                .andExpect(jsonPath("$.minimumStockLevel").value(10))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteInventoryTest() throws Exception {
        doNothing().when(inventoryService).deleteInventory(1L);
        mockMvc.perform(delete("/api/v1/inventories/1"))
                .andExpect(status().isNoContent());
        verify(inventoryService, times(1)).deleteInventory(1L);
    }
}
