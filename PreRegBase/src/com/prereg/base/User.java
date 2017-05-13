package com.prereg.base;


import com.prereg.base.data.PreRegProto;

public final class User {
    private PreRegProto.UserData userData;

    public User(PreRegProto.UserData userData) {
        this.userData = userData;
    }

    public int getId() {
        return userData.getId();
    }

    public String getUserName() {
        return userData.getUsername();
    }

    public String getName() {
        return userData.getName();
    }

    public String getEmail() {
        return userData.getEmail();
    }
}
