package com.service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum DataType {
    STRING("String"),
    NUMBER("Number"),
    TEXT("Text"),
    DATETIME("Datetime"),
    SINGLE_VALUE_LIST("SingleValueList"),
    MULTI_VALUE_LIST("MultiValueList"),
    FILE("File");

    private String value;

    @JsonCreator
    public static DataType fromValue(String text) {
        for (DataType b : DataType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
