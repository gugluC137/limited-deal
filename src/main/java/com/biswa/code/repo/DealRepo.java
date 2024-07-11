package com.biswa.code.repo;

import com.biswa.code.exception.InvalidDealIdException;
import com.biswa.code.model.Deal;
import lombok.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DealRepo {
    private final Map<String, Deal> persistedDeals;
    private static final Integer DEFAULT_DEAL_PERIOD_IN_MIN = 120;

    public DealRepo() {
        this.persistedDeals = new HashMap<>();
    }

    public void addNewDeal(@NonNull Deal newDeal) {
        this.persistedDeals.put(newDeal.getId(), newDeal);
    }

    public Deal getDealById(@NonNull String id) {
        if (!persistedDeals.containsKey(id)) {
            throw new InvalidDealIdException();
        }

        return persistedDeals.get(id);
    }

    public void updateEndDate(@NonNull String dealId, @NonNull Date endDate) {
        this.persistedDeals.get(dealId).updateEndDate(endDate);
    }

    public Integer getDefaultDealPeriodInMin() {
        return DEFAULT_DEAL_PERIOD_IN_MIN;
    }
}
