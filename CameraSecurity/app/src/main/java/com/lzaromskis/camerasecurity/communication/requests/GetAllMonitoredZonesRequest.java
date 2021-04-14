package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketData;

public class GetAllMonitoredZonesRequest extends BaseRequest {

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        return createMinimalPacket(true);
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.GET_ZONES.getValue();
    }
}
