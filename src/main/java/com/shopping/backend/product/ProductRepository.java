package com.shopping.backend.product;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * skorbas: this is just dummy repository for the purpose of this exercise (can be H2 or any other relational DB).
 */
@Repository
public class ProductRepository {

    Map<UUID, Product> products = new HashMap<>();

    public Product add(ProductDto productDto) {
        final Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();

        products.put(product.getId(), product);
        return product;
    }

    public Product findById(UUID id) {
        return products.get(id);
    }
}
