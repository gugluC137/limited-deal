package com.biswa.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateRequest {
    private UpdateType updateType;
    private String id;
    private String updateValue;
}
