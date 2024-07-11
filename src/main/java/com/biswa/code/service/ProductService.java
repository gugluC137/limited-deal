package com.biswa.code.service;

import com.biswa.code.exception.InvalidProductIdException;
import com.biswa.code.exception.InvalidProductNameException;
import com.biswa.code.exception.LimitedDealException;
import com.biswa.code.model.Product;
import com.biswa.code.repo.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.UUID;

import static com.biswa.code.exception.ErrorMessageConstants.INVALID_PRODUCT_ID;

@AllArgsConstructor
public class ProductService {
    private final ProductRepo repo;

    public String addNewProduct(@NonNull final String name, @NonNull final Double price) {
        validateProductDetails(name, price);
        String productId = UUID.randomUUID().toString();

        Product newProduct = new Product(productId, name, price);
        repo.addNewProduct(newProduct);

        return productId;
    }

    private void validateProductDetails(@NonNull final String name, @NonNull final Double price) {
        if (name.isBlank()) {
            throw new InvalidProductNameException();
        }
    }

    public Product getProductById(@NonNull String productId) {
        try {
            return repo.getProductById(productId);
        } catch (InvalidProductIdException ex) {
            throw new LimitedDealException(INVALID_PRODUCT_ID);
        }
    }

    public boolean isProductPresent(@NonNull String productId) {
        try {
            return Objects.nonNull(repo.getProductById(productId));
        } catch (InvalidProductIdException ex) {
            return false;
        }
    }
}
