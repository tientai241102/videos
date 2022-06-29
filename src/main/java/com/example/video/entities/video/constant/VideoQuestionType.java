package com.example.video.entities.video.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VideoQuestionType {
    MultiSelect,
    Explain,
    OneSelect,;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
