package com.biswa.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateDealRequestDto {
    private String name;
    private Date startDate;
    private Integer periodInMin;
    private List<ProductDealRequestDto> productDealList;
}
