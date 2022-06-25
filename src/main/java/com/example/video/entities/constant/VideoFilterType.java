package com.example.video.entities.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VideoFilterType {
    rate,
    view;
    @JsonValue
    public int toValue() {
        return ordinal();
    }

}
