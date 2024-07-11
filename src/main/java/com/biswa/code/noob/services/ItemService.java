package com.biswa.code.noob.services;

import com.biswa.code.noob.model.Item;

import java.util.Map;
import java.util.UUID;

public class ItemService {
    private Map<String, Item> mapOfItems;

    public Item addNewItem(final String itemName) {
        if (mapOfItems.containsKey(itemName)) {
            return mapOfItems.get(itemName);
        }

        String itemId = UUID.randomUUID().toString();
        Item newItem = new Item(itemId, itemName);
        mapOfItems.put(itemName, newItem);

        return newItem;
    }

    public Item getItem(String id) {
        return this.mapOfItems.get(id);
    }
}
