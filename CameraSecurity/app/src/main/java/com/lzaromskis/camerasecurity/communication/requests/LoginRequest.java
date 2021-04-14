package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

public class LoginRequest extends BaseRequest {

    String _password;

    public LoginRequest(String password) {
        _password = password;
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(false);
        packet.addAttribute(PacketAttribute.PASSWORD.getValue(), _password);
        return packet;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.LOGIN.getValue();
    }
}
