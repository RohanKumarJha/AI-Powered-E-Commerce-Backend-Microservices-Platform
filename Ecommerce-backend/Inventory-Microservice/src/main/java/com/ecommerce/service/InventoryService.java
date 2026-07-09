package com.ecommerce.service;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.dto.request.UpdateInventoryRequest;
import com.ecommerce.dto.request.UpdateStockRequest;
import com.ecommerce.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    List<InventoryResponse> getAllInventories();

    InventoryResponse getInventoryById(Long inventoryId);

    InventoryResponse getInventoryByProductId(Long productId);

    InventoryResponse updateInventory(Long inventoryId, UpdateInventoryRequest request);

    InventoryResponse updateStock(Long inventoryId, UpdateStockRequest request);

    void deleteInventory(Long inventoryId);
}