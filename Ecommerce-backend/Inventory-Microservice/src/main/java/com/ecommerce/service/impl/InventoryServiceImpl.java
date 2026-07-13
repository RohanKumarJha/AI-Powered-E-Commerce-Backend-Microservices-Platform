package com.ecommerce.service.impl;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.dto.request.UpdateInventoryRequest;
import com.ecommerce.dto.request.UpdateStockRequest;
import com.ecommerce.dto.response.InventoryResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.factory.InventoryFactory;
import com.ecommerce.mapper.InventoryMapper;
import com.ecommerce.model.Inventory;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.service.InventoryService;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import com.ecommerce.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryFactory inventoryFactory;
    private final InventoryMapper inventoryMapper;


    @Override
    public InventoryResponse createInventory(InventoryRequest request) {

        Inventory inventory = inventoryFactory.create(request);

        Long userId = UserContext.getCurrentUserId();

        inventory.setCreatedBy(userId);
        inventory.setUpdatedBy(userId);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(savedInventory);
    }


    @Override
    public PageResponse<InventoryResponse> getAllInventories(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir) {


        Page<InventoryResponse> result =
                inventoryRepository.findAll(
                                PageRequestUtil.createPageRequest(
                                        page,
                                        size,
                                        sortBy,
                                        sortDir
                                )
                        )
                        .map(inventoryMapper::toResponse);


        return PageResponseUtil.from(result);
    }


    @Override
    public InventoryResponse getInventoryById(Long inventoryId) {


        Inventory inventory =
                inventoryFactory.getById(inventoryId);


        return inventoryMapper.toResponse(inventory);
    }


    @Override
    public InventoryResponse getInventoryByProductId(Long productId) {


        Inventory inventory =
                inventoryFactory.getByProductId(productId);


        return inventoryMapper.toResponse(inventory);
    }


    @Override
    public InventoryResponse updateInventory(
            Long inventoryId,
            UpdateInventoryRequest request) {


        Inventory inventory =
                inventoryFactory.getById(inventoryId);


        inventoryFactory.validateSkuForUpdate(
                inventoryId,
                request.getSku()
        );


        inventoryMapper.updateEntity(
                request,
                inventory
        );


        inventory.setUpdatedBy(
                UserContext.getCurrentUserId()
        );


        Inventory updatedInventory =
                inventoryRepository.save(inventory);


        return inventoryMapper.toResponse(updatedInventory);
    }


    @Override
    public InventoryResponse updateStock(
            Long inventoryId,
            UpdateStockRequest request) {


        Inventory inventory =
                inventoryFactory.getById(inventoryId);


        inventory.setAvailableQuantity(
                request.getQuantity()
        );


        inventory.setUpdatedBy(
                UserContext.getCurrentUserId()
        );


        Inventory updatedInventory =
                inventoryRepository.save(inventory);


        return inventoryMapper.toResponse(updatedInventory);
    }


    @Override
    public void deleteInventory(Long inventoryId) {


        Inventory inventory =
                inventoryFactory.getById(inventoryId);


        inventoryRepository.delete(inventory);
    }
}