package com.biswa.code.exception;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class LimitedDealException extends RuntimeException {
    public LimitedDealException(@NonNull String message) {
        super(message);
    }
}
