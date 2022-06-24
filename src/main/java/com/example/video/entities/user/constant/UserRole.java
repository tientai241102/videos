package com.example.video.entities.user.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_STAFF;

    @JsonValue
    public int toValue() {
        return ordinal();
    }

}
