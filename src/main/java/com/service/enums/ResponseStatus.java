package com.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED");

    private String value;
}
