package com.biswa.code.noob.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
@Getter
public class Deal {
    private String id;
    private Map<Item, Integer> items;

    private Date startTime;
    private Date endTime;

    private Boolean isActive;

    public void endDeal() {
        this.isActive = false;
    }

    public void updateEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void updateItemQuantity(final Item item, final int quantity) {
        items.put(item, quantity);
    }

    public boolean validateItems(final List<Item> itemList) {
        Set<Item> itemsSelected = new HashSet<>();
        for (Item item : itemList) {
            if (!this.items.containsKey(item)) {
                return false;
            }
            if (itemsSelected.contains(item)) {
                return false;
            }
            //quantity

            itemsSelected.add(item);
        }

        return true;
    }

//    private int intervalInMin;
//
//    public boolean isActive() {
//        Instant expiryTime = this.startTime.toInstant().plusSeconds(intervalInMin* 60L);
//        Instant curTime = Instant.now();
//
//        return curTime.isBefore(expiryTime);
//    }
}
