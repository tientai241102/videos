package com.example.video.entities.request;

import com.example.video.entities.user.User;
import org.hibernate.annotations.NaturalId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserRequest extends User {


    private String oldPass;

    private String newPass;


    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
