package com.ecommerce.service.client;

import com.ecommerce.dto.client.ProductResponse;

public interface CatalogClient {

    ProductResponse getProductById(Long productId);
}
