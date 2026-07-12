package com.ecommerce.client.inventory;

import org.springframework.stereotype.Service;

@Service
public class MockInventoryClient implements InventoryClient {

    @Override
    public Integer getAvailableQuantity(Long productId) {
        return 100;
    }

    @Override
    public void reduceStock(Long productId, Integer quantity) {
        System.out.println("This is reduce stock method");
    }
}