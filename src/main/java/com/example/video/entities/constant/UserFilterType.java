package com.example.video.entities.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserFilterType {
    follow;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
