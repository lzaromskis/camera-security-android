package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

public class LoginRequest extends BaseRequest {

    String _password;

    public LoginRequest(String password) {
        _password = password;
        _requestCode = RequestCode.LOGIN;
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket();
        packet.addAttribute(PacketAttribute.PASSWORD.getValue(), _password);
        return packet;
    }
}
