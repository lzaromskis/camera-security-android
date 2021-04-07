package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketData;

public class CameraFeedRequest extends BaseRequest {

    public CameraFeedRequest() {
        _requestCode = RequestCode.GET_IMAGE;
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket();
        addSecret(packet);
        return packet;
    }
}
