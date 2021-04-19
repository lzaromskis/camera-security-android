package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

public class CameraFeedRequest extends BaseRequest {

    private final boolean _drawZones;
    private final boolean _drawDetections;

    public CameraFeedRequest(boolean drawZones, boolean drawDetections) {
        _drawZones = drawZones;
        _drawDetections = drawDetections;
    }

    @Override
    public PacketData getPacketData() throws UserNotAuthenticatedException {
        PacketData packet = createMinimalPacket(true);
        if (_drawZones)
            packet.addAttribute(PacketAttribute.DRAW_ZONES.getValue(), "true");
        if (_drawDetections)
            packet.addAttribute(PacketAttribute.DRAW_DETECTIONS.getValue(), "true");
        return packet;
    }

    @Override
    protected int getRequestCode() {
        return RequestCode.GET_IMAGE.getValue();
    }
}
