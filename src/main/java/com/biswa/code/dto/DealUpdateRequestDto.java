package com.biswa.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class DealUpdateRequestDto {
    private String dealId;
    List<UpdateRequest> updateRequestList;
}
