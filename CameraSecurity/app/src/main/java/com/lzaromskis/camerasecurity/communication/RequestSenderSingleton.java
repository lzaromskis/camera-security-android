package com.lzaromskis.camerasecurity.communication;

import android.security.keystore.UserNotAuthenticatedException;

import androidx.fragment.app.Fragment;

import com.lzaromskis.camerasecurity.communication.requests.IRequest;
import com.lzaromskis.camerasecurity.exceptions.InvalidHostnameException;
import com.lzaromskis.camerasecurity.exceptions.InvalidResponseException;
import com.lzaromskis.camerasecurity.exceptions.NoResponseException;
import com.lzaromskis.camerasecurity.exceptions.SendingRequestException;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class RequestSenderSingleton {

    private static class LazyHolder {
        static final RequestSenderSingleton INSTANCE = new RequestSenderSingleton();
    }

    private final PacketDataSerializer serializer;

    private RequestSenderSingleton() {
        serializer = new PacketDataSerializer();
    }

    public PacketData sendRequest(Fragment fragment, IRequest request) throws UserNotAuthenticatedException, SendingRequestException, NoResponseException, SocketTimeoutException {
        try {
            PacketData packet = request.getPacketData();
            String hostname = SharedPrefs.readString(SharedPrefs.HOSTNAME);
            if (hostname.equals(""))
                hostname = "10.0.2.2";
            Client client = new Client(hostname);
            String response = client.sendRequest(serializer.serialize(packet));
            if (response == null)
                throw new NoResponseException("Connection timed out");
            return serializer.deserialize(response);
        } catch (SocketTimeoutException ex) {
            throw ex;
        } catch (IOException | InvalidHostnameException | InvalidResponseException e) {
            throw new SendingRequestException(e.getMessage());
        }
    }

    public static RequestSenderSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

}
