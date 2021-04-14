package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketData;

public class GetLatestAlertsRequest extends BaseRequest {
    @Override
    protected int getRequestCode() {
        return RequestCode.GET_ALERT_LIST.getValue();
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        return createMinimalPacket(true);
    }
}
