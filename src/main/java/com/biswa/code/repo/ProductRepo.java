package com.biswa.code.repo;

import com.biswa.code.exception.InvalidProductIdException;
import com.biswa.code.model.Product;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ProductRepo {
    private final Map<String, Product> persistedProducts;

    public ProductRepo() {
        this.persistedProducts = new HashMap<>();
    }

    public void addNewProduct(@NonNull Product newProduct) {
        persistedProducts.put(newProduct.getId(), newProduct);
    }

    public Product getProductById(@NonNull String productId) {
        if (!persistedProducts.containsKey(productId)) {
            throw new InvalidProductIdException();
        }

        return persistedProducts.get(productId);
    }
}
