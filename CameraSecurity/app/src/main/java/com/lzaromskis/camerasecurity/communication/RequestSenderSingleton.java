package com.lzaromskis.camerasecurity.communication;

import android.content.Context;
import android.security.keystore.UserNotAuthenticatedException;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lzaromskis.camerasecurity.communication.requests.IRequest;
import com.lzaromskis.camerasecurity.exceptions.InvalidHostnameException;
import com.lzaromskis.camerasecurity.helpers.SharedPrefs;
import com.lzaromskis.camerasecurity.ui.login.LoginFragment;

import java.io.IOException;

public class RequestSenderSingleton {

    private static class LazyHolder {
        static final RequestSenderSingleton INSTANCE = new RequestSenderSingleton();
    }

    private final PacketDataSerializer serializer;

    private RequestSenderSingleton() {
        serializer = new PacketDataSerializer();
    }

    public PacketData sendRequest(Fragment fragment, IRequest request) {
        try {
            PacketData packet = request.getPacketData();
            String hostname = SharedPrefs.readString(SharedPrefs.HOSTNAME);
            if (hostname.equals(""))
                hostname = "10.0.2.2";
            Client client = new Client(hostname);
            String response = client.sendRequest(serializer.serialize(packet));
            return serializer.deserialize(response);
        } catch (UserNotAuthenticatedException e) {
            Fragment newFragment = new LoginFragment();
            FragmentManager manager = fragment.getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(fragment.getId(), newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidHostnameException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static RequestSenderSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

}
