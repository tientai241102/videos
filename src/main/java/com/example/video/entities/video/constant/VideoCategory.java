package com.example.video.entities.video.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VideoCategory {
    computer_science,
    general_education,
    painting,
    economics,
    language,
    literacy,
    math,
    algebra,
    calculus,
    geometry;
    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
