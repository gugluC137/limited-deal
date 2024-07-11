package com.biswa.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDealRequestDto {
    private String productId;
    private Integer quantity;
    private Double dealPrice;
}
