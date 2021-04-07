package com.lzaromskis.camerasecurity.communication;

public enum PacketAttribute {
    CODE("code"),
    PASSWORD("password"),
    PASSWORD_NEW("password_new"),
    SECRET("secret"),
    IMAGE("image"),
    MESSAGE("message");

    private String _value;

    PacketAttribute(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }
}
