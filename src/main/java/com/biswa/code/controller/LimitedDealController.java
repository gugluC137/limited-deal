package com.biswa.code.controller;

import com.biswa.code.dto.CommonResponseEntity;
import com.biswa.code.dto.CreateDealRequestDto;
import com.biswa.code.service.DealService;
import lombok.NonNull;

public class LimitedDealController {
    private DealService dealService;

    public CommonResponseEntity createNewDeal(@NonNull CreateDealRequestDto requestDto) {
        String dealId = dealService.createNewDeal(requestDto);
        return "New Deal created with dealId: " + dealId;
    }

    public CommonResponseEntity endDeal(@NonNull String dealId) {
        dealService.endDeal(dealId);
        return "Deal with id: " + dealId + " has ended";
    }

    public CommonResponseEntity claimDeal(@NonNull String dealId, @NonNull String productId) {
        dealService.claimDeal(dealId, productId);
        return "Deal has been claimed successfully";
    }
}
