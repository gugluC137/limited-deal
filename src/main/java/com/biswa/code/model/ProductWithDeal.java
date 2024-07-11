package com.biswa.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ProductWithDeal {
    private String productId;
    private String dealId;

    private Integer quantity;
    private Double dealPrice;

    public void updateDealPrice(@NonNull Double newDealPrice) {
        this.dealPrice = newDealPrice;
    }

    public void updateQuantity(@NonNull Integer updatedQuantity) {
        this.quantity = updatedQuantity;
    }
}
