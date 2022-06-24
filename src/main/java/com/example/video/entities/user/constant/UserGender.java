package com.example.video.entities.user.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserGender {
    MAN,
    GIRL;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
