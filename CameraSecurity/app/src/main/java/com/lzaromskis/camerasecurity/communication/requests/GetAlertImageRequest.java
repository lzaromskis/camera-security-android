package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;

public class GetAlertImageRequest extends BaseRequest {

    private final String _alertName;

    public GetAlertImageRequest(String alertName) {
        _alertName = alertName;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.GET_ALERT_IMAGE.getValue();
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(true);
        packet.addAttribute(PacketAttribute.ALERT.getValue(), _alertName);
        return packet;
    }
}
