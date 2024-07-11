package com.biswa.code.noob.controller;

import com.biswa.code.noob.model.DealUpdateRequestDto;
import com.biswa.code.noob.model.ItemClaimRequestDto;
import com.biswa.code.noob.model.ItemRequestDto;
import com.biswa.code.noob.services.DealService;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class DealController {
    private DealService dealService;

//    Create a deal with the price and number of items to be sold as part of the deal
    public String createNewDeal(@NonNull List<ItemRequestDto> itemsToBeAdded, Date startTime, @NonNull Date endTime) {
        if (Objects.isNull(startTime)) {
            startTime = new Date(System.currentTimeMillis());
        }
        String dealId = dealService.createDeal(itemsToBeAdded, startTime, endTime);
        return "Deal has been created with deal ID: " + dealId;
    }


//    End a deal
    public void endDeal(@NonNull final String dealId) {
        dealService.endDeal(dealId);
        //return string
    }


//    Update a deal to increase the number of items or end time
    public void updateDeal(@NonNull final String dealId, DealUpdateRequestDto updateRequestDto) {
        dealService.updateDeal(dealId, updateRequestDto);
        //return string
    }

//    Claim a deal

    public String claimDeal(@NonNull final String dealId, @NonNull final List<ItemClaimRequestDto> itemClaimRequestDtos) {
        return dealService.claimDeal(dealId, itemClaimRequestDtos);
    }


}
