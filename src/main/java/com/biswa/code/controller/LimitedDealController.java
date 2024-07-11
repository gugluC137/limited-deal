package com.biswa.code.controller;

import com.biswa.code.dto.CommonResponseDto;
import com.biswa.code.dto.CreateDealRequestDto;
import com.biswa.code.dto.DealUpdateRequestDto;
import com.biswa.code.exception.LimitedDealException;
import com.biswa.code.service.DealService;
import lombok.NonNull;

import static com.biswa.code.constants.CommonResponseMessageConstants.*;

public class LimitedDealController {
    private final DealService dealService;

    public LimitedDealController(DealService dealService) {
        this.dealService = dealService;
    }

    public CommonResponseDto createNewDeal(@NonNull CreateDealRequestDto requestDto) {
        try {
            String dealId = dealService.createNewDeal(requestDto);
            return CommonResponseDto.getSuccessResponse(DEAL_CREATED + dealId);
        } catch (LimitedDealException ex) {
            return CommonResponseDto.getErrorResponse(ex);
        }
    }

    public CommonResponseDto endDeal(@NonNull String dealId) {
        try {
            dealService.endDeal(dealId);
            return CommonResponseDto.getSuccessResponse(DEAL_ENDED + dealId);
        } catch (LimitedDealException ex) {
            return CommonResponseDto.getErrorResponse(ex);
        }
    }

    public CommonResponseDto claimDeal(@NonNull String dealId, @NonNull String productId) {
        try {
            dealService.claimDeal(dealId, productId);
            return CommonResponseDto.getSuccessResponse(DEAL_CLAIM);
        } catch (LimitedDealException ex) {
            return CommonResponseDto.getErrorResponse(ex);
        }
    }

    public CommonResponseDto updateDeal(@NonNull DealUpdateRequestDto requestDto) {
        try {
            dealService.updateDeal(requestDto);
            return CommonResponseDto.getSuccessResponse(DEAL_UPDATED);
        } catch (LimitedDealException ex) {
            return CommonResponseDto.getErrorResponse(ex);
        }
    }
}
