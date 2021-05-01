package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

public class ChangePasswordRequest extends BaseRequest {

    private final String _currentPassword;
    private final String _newPassword;

    public ChangePasswordRequest(String currentPassword, String newPassword) {
        _currentPassword = currentPassword;
        _newPassword = newPassword;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.CHANGE_PASSWORD.getValue();
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(true);
        packet.addAttribute(PacketAttribute.PASSWORD.getValue(), _currentPassword);
        packet.addAttribute(PacketAttribute.PASSWORD_NEW.getValue(), _newPassword);
        return packet;
    }
}
