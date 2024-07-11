package com.biswa.code.noob.services;

import com.biswa.code.noob.model.*;

import java.util.*;

public class DealService {
    private Map<String, Deal> deals;

    private ItemService itemService;

    public String createDeal(final List<ItemRequestDto> itemsToBeAdded, Date startTime, final Date endTime) {
        String dealId = UUID.randomUUID().toString();
        Map<Item, Integer> itemsForTheDeal = getMapOfItems(itemsToBeAdded);
        boolean isActive = startTime.before(new Date(System.currentTimeMillis()));
        Deal newDeal = new Deal(dealId, itemsForTheDeal, startTime, endTime, isActive);
        deals.put(dealId, newDeal);

        return newDeal.getId();
    }

    private Map<Item, Integer> getMapOfItems(final List<ItemRequestDto> itemsToBeAdded) {
        Map<Item, Integer> mapOfItems = new HashMap<>();

        for (ItemRequestDto itemDto : itemsToBeAdded) {
            mapOfItems.put(itemService.addNewItem(itemDto.getItemName()), itemDto.getQuantity());
        }

        return mapOfItems;
    }

    public void endDeal(final String dealId) {
        if (!deals.containsKey(dealId)) {
            //throw exceptions
        }

        deals.get(dealId).endDeal();
    }

    public void updateDeal(final String dealId, final DealUpdateRequestDto updateRequestDto) {
        if (!deals.containsKey(dealId)) {
            //throw exception
        }

        Deal deal = deals.get(dealId);

        for (Map.Entry<UpdateType, Object> updateEntry : updateRequestDto.getUpdateTypeStringMap().entrySet()) {
            switch (updateEntry.getKey()) {
                case END_TIME : deal.updateEndTime((Date) updateEntry.getValue());
                break;
                case QUANTITY: updateItemQuantity(deal, (DealItemUpdateRequestDto)updateEntry.getValue());
                default: //throw exception
            }
        }
    }

    private void updateItemQuantity(Deal deal, DealItemUpdateRequestDto updateRequestDto) {
        Item item = itemService.getItem(updateRequestDto.getItemId());
        if (!deal.getItems().containsKey(item)) {
            //throw exception
        }

        deal.updateItemQuantity(item, updateRequestDto.getQuantity());
    }

    public String claimDeal(final String dealId, final List<ItemClaimRequestDto> itemClaimRequestDtoList) {
        if (!deals.containsKey(dealId)) {
            System.out.println("dealId is not valid");
        }
        Deal deal = deals.get(dealId);

        if (!deal.getIsActive()) {
            System.out.println("deal is not active");
        }

        List<Item> itemsSelected = new ArrayList<>();
        for (ItemClaimRequestDto itemClaimRequestDto : itemClaimRequestDtoList) {
            itemsSelected.add(itemService.getItem(itemClaimRequestDto.getItemId()));
        }

        if(!deal.validateItems(itemsSelected)) {
            //throw exception
        }

        return "Deal Claimed";

    }


}
