package com.biswa.code.dto;

import com.biswa.code.exception.LimitedDealException;
import lombok.Builder;

@Builder
public class CommonResponseDto {
    private boolean isSuccess;
    private String message;

    public static CommonResponseDto getSuccessResponse(String message) {
        return CommonResponseDto.builder()
            .isSuccess(Boolean.TRUE)
            .message(message)
            .build();
    }

    public static CommonResponseDto getErrorResponse(LimitedDealException ex) {
        return CommonResponseDto.builder()
            .isSuccess(Boolean.FALSE)
            .message(ex.getMessage())
            .build();
    }
}
