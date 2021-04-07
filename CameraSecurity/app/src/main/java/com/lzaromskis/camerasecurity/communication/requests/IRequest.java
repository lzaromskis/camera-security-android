package com.lzaromskis.camerasecurity.communication.requests;

import android.security.keystore.UserNotAuthenticatedException;

import com.lzaromskis.camerasecurity.communication.PacketData;

public interface IRequest {
    public PacketData getPacketData() throws UserNotAuthenticatedException;
}
