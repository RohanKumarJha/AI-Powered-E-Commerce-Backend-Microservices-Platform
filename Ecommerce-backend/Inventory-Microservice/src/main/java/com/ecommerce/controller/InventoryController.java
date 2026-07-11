package com.ecommerce.controller;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.dto.request.UpdateInventoryRequest;
import com.ecommerce.dto.request.UpdateStockRequest;
import com.ecommerce.dto.response.InventoryResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(
            @Valid @RequestBody InventoryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<InventoryResponse>> getAllInventories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {

        return ResponseEntity.ok(
                inventoryService.getAllInventories(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponse> getInventoryById(
            @PathVariable Long inventoryId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryById(inventoryId)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByProductId(productId)
        );
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponse> updateInventory(
            @PathVariable Long inventoryId,
            @Valid @RequestBody UpdateInventoryRequest request) {

        return ResponseEntity.ok(
                inventoryService.updateInventory(
                        inventoryId,
                        request
                )
        );
    }

    @PatchMapping("/{inventoryId}/stock")
    public ResponseEntity<InventoryResponse> updateStock(
            @PathVariable Long inventoryId,
            @Valid @RequestBody UpdateStockRequest request) {

        return ResponseEntity.ok(
                inventoryService.updateStock(
                        inventoryId,
                        request
                )
        );
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(
            @PathVariable Long inventoryId) {

        inventoryService.deleteInventory(inventoryId);

        return ResponseEntity.noContent().build();
    }
}