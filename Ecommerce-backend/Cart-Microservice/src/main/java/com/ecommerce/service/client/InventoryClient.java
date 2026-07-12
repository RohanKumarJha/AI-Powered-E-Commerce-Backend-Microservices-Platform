package com.ecommerce.service.client;

public interface InventoryClient {

    void decreaseStock(Long productId, Integer quantity);

    void increaseStock(Long productId, Integer quantity);
}
