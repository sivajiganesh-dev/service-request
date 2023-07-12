package com.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Condition {
    EQ("="),
    IN("in");

    private String value;
}
