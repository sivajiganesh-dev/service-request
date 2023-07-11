package com.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum DataType {
    STRING("String"),
    NUMBER("Number"),
    TEXT("Text"),
    DATETIME("Datetime"),
    SINGLEVALUELIST("SingleValueList"),
    MULTIVALUELIST("MultiValueList"),
    FILE("File");

    private String value;
}
