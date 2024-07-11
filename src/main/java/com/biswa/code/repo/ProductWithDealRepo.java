package com.biswa.code.repo;

import com.biswa.code.exception.InvalidDealIdException;
import com.biswa.code.exception.ProductNotInDealException;
import com.biswa.code.model.ProductWithDeal;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ProductWithDealRepo {
    private final Map<String, Map<String, ProductWithDeal>> persistedDealDetails;

    public ProductWithDealRepo() {
        persistedDealDetails = new HashMap<>();
    }

    public void storeNewProductWIthDeal(@NonNull ProductWithDeal productWithDeal) {
        if (!persistedDealDetails.containsKey(productWithDeal.getDealId())) {
            persistedDealDetails.put(productWithDeal.getDealId(), new HashMap<>());
        }

        persistedDealDetails.get(productWithDeal.getDealId())
            .put(productWithDeal.getProductId(), productWithDeal);
    }

    public Map<String, ProductWithDeal> getAllProductsInDeal(@NonNull String dealId) {
        if (!persistedDealDetails.containsKey(dealId)) {
            throw new InvalidDealIdException();
        }

        return persistedDealDetails.get(dealId);
    }

    public ProductWithDeal getProductInDeal(@NonNull String dealId, @NonNull String productId) {
        Map<String, ProductWithDeal> allProductInDeal = getAllProductsInDeal(dealId);

        if (!allProductInDeal.containsKey(productId)) {
            throw new ProductNotInDealException();
        }

        return allProductInDeal.get(productId);
    }

    public void updateQuantity(@NonNull String dealId, @NonNull String productId,
                               @NonNull Integer updatedQuantity) {
        ProductWithDeal pwd = getProductInDeal(dealId, productId);

        pwd.updateQuantity(updatedQuantity);
    }

    public void updatePrice(@NonNull String dealId, @NonNull String productId,
                            @NonNull Double updatedPrice) {
        ProductWithDeal pwd = getProductInDeal(dealId, productId);

        pwd.updateDealPrice(updatedPrice);
    }

}
