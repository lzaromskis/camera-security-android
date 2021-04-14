package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

public class DeleteMonitoredZoneRequest extends BaseRequest {

    private final String _name;

    public DeleteMonitoredZoneRequest(String name) {
        _name = name;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.DELETE_ZONE.getValue();
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(true);
        packet.addAttribute(PacketAttribute.ZONE_NAME.getValue(), _name);
        return packet;
    }
}
