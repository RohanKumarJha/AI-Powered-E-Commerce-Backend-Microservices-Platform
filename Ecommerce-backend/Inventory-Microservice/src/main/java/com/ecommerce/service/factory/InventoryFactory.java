package com.ecommerce.service.factory;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.InventoryMapper;
import com.ecommerce.model.Inventory;
import com.ecommerce.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryFactory {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    /**
     * Create Inventory Entity
     */
    public Inventory create(InventoryRequest request) {

        validateProductId(request.getProductId());
        validateSku(request.getSku());

        return inventoryMapper.toEntity(request);
    }

    public Inventory getById(Long inventoryId) {

        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory",
                                "id",
                                inventoryId
                        ));
    }

    public Inventory getByProductId(Long productId) {

        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory",
                                "productId",
                                productId
                        ));
    }

    /**
     * Validate Duplicate Product
     */
    public void validateProductId(Long productId) {

        if (inventoryRepository.existsByProductId(productId)) {
            throw new ResourceAlreadyExistsException(
                    "Inventory",
                    "productId",
                    productId
            );
        }
    }

    /**
     * Validate Duplicate SKU
     */
    public void validateSku(String sku) {

        if (inventoryRepository.existsBySkuIgnoreCase(sku)) {
            throw new ResourceAlreadyExistsException(
                    "Inventory",
                    "sku",
                    sku
            );
        }
    }

    /**
     * Validate SKU For Update
     */
    public void validateSkuForUpdate(Long inventoryId, String sku) {

        if (inventoryRepository.existsBySkuIgnoreCaseAndInventoryIdNot(sku, inventoryId)) {
            throw new ResourceAlreadyExistsException("Inventory", "sku", sku
            );
        }
    }
}