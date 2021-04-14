package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

public class SetMonitoredZoneActiveStateRequest extends BaseRequest {

    private final String _name;
    private final boolean _isActive;

    public SetMonitoredZoneActiveStateRequest(String name, boolean isActive) {
        _name = name;
        _isActive = isActive;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.SET_ZONE_ACTIVITY.getValue();
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(true);
        packet.addAttribute(PacketAttribute.ZONE_NAME.getValue(), _name);
        packet.addAttribute(PacketAttribute.ZONE_ACTIVE.getValue(), _isActive ? "true" : "false");
        return packet;
    }
}
