package com.biswa.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Deal {
    private String id;
    private String name;
    private Date startDate;
    private Date endDate;

    public void updateEndDate(@NonNull Date newEndDate) {
        this.endDate = newEndDate;
    }
}
