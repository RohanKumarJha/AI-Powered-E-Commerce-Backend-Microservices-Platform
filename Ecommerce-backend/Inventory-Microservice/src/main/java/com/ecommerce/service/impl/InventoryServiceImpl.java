package com.ecommerce.service.impl;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.dto.request.UpdateInventoryRequest;
import com.ecommerce.dto.request.UpdateStockRequest;
import com.ecommerce.dto.response.InventoryResponse;
import com.ecommerce.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        return null;
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        return List.of();
    }

    @Override
    public InventoryResponse getInventoryById(Long inventoryId) {
        return null;
    }

    @Override
    public InventoryResponse getInventoryByProductId(Long productId) {
        return null;
    }

    @Override
    public InventoryResponse updateInventory(Long inventoryId, UpdateInventoryRequest request) {
        return null;
    }

    @Override
    public InventoryResponse updateStock(Long inventoryId, UpdateStockRequest request) {
        return null;
    }

    @Override
    public void deleteInventory(Long inventoryId) {

    }
}
