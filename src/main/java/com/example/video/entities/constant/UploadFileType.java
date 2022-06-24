package com.example.video.entities.constant;


import com.fasterxml.jackson.annotation.JsonValue;

public enum UploadFileType {
    IMAGE,
    VIDEO_NATIVE;

    @JsonValue
    public int toValue() {
        return ordinal();
    }

}
