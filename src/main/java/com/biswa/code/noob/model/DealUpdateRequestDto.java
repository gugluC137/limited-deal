package com.biswa.code.noob.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class DealUpdateRequestDto {
    private Map<UpdateType, Object> updateTypeStringMap;
}
