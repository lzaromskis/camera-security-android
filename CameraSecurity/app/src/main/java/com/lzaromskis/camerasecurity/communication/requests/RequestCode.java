package com.lzaromskis.camerasecurity.communication.requests;

import com.lzaromskis.camerasecurity.exceptions.InvalidEnumValueException;

import java.util.HashMap;
import java.util.Map;

public enum RequestCode {
    LOGIN(15),
    CHANGE_PASSWORD(25),
    GET_IMAGE(35),
    GET_IMAGE_RAW(45),
    GET_ZONES(55),
    CREATE_ZONE(65),
    SET_ZONE_ACTIVITY(75),
    DELETE_ZONE(85),
    GET_ALERT_LIST(95),
    GET_ALERT_IMAGE(105),
    GET_HEATMAP(115),
    CREATE_ZONE_INIT(125);

    private static final Map<Integer, RequestCode> intToTypeMap = new HashMap<Integer, RequestCode>();
    static {
        for (RequestCode type : RequestCode.values()) {
            intToTypeMap.put(type._value, type);
        }
    }

    public static RequestCode fromInt(int i) throws InvalidEnumValueException {
        RequestCode type = intToTypeMap.get(i);
        if (type == null)
            throw new InvalidEnumValueException("Request code '" + i + "' does not exist.");
        return type;
    }

    private final int _value;

    RequestCode(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }
}
