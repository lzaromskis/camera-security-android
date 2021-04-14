package com.lzaromskis.camerasecurity.communication;

public enum PacketAttribute {
    CODE("code"),
    PASSWORD("password"),
    PASSWORD_NEW("password_new"),
    SECRET("secret"),
    IMAGE("image"),
    MESSAGE("message"),
    ZONES("monitored_zones"),
    ZONE("zone"),
    ZONE_NAME("zone_name"),
    ZONE_ACTIVE("zone_active"),
    ALERT_LIST("alert_list"),
    ALERT("alert");

    private final String _value;

    PacketAttribute(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }
}
