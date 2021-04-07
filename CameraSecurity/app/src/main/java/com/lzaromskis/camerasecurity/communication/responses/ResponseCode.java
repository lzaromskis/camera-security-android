package com.lzaromskis.camerasecurity.communication.responses;

import com.lzaromskis.camerasecurity.exceptions.InvalidEnumValueException;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
    OK(10),
    NOT_AUTHENTICATED(20),
    INVALID_PACKET(30),
    BAD_PASSWORD(40),
    INVALID_REQUEST(50);

    private static final Map<Integer, ResponseCode> intToTypeMap = new HashMap<Integer, ResponseCode>();
    static {
        for (ResponseCode type : ResponseCode.values()) {
            intToTypeMap.put(type._value, type);
        }
    }

    public static ResponseCode fromInt(int i) throws InvalidEnumValueException {
        ResponseCode type = intToTypeMap.get(i);
        if (type == null)
            throw new InvalidEnumValueException("Response code '" + i + "' does not exist.");
        return type;
    }

    private final int _value;

    ResponseCode(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }
}
