package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.monitoring.serializers.MonitoredZoneSerializer;

public class AddMonitoredZoneRequest extends BaseRequest {

    private final MonitoredZone _zone;

    public AddMonitoredZoneRequest(MonitoredZone zone) {
        _zone = zone;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.CREATE_ZONE.getValue();
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(true);
        MonitoredZoneSerializer serializer = new MonitoredZoneSerializer();
        packet.addAttribute(PacketAttribute.ZONE.getValue(), serializer.serialize(_zone));
        return packet;
    }
}
