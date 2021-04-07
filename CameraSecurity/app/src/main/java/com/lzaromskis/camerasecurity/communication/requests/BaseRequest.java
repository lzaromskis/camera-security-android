package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.helpers.SharedPrefs;

public abstract class BaseRequest implements IRequest {

    protected RequestCode _requestCode;

    protected final PacketData createMinimalPacket() {
        PacketData packet = new PacketData();
        packet.addAttribute(PacketAttribute.CODE.getValue(), String.valueOf(_requestCode.getValue()));
        return packet;
    }

    protected final void addSecret(PacketData packet) throws UserNotAuthenticatedException {
        if (!SharedPrefs.readBoolean(SharedPrefs.IS_SECRET_VALID))
            throw new UserNotAuthenticatedException("The secret is expired");
        String secret = SharedPrefs.readString(SharedPrefs.SECRET);
        packet.addAttribute(PacketAttribute.SECRET.getValue(), secret);
    }
}
