package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketData;

public class GetDataForCreatingZoneRequest extends BaseRequest {
    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        return createMinimalPacket(true);
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.CREATE_ZONE_INIT.getValue();
    }
}
