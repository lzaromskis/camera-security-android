package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.RequestSenderSingleton;
import com.lzaromskis.camerasecurity.communication.requests.IRequest;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;
import com.lzaromskis.camerasecurity.exceptions.InvalidResponseException;
import com.lzaromskis.camerasecurity.exceptions.NoResponseException;
import com.lzaromskis.camerasecurity.exceptions.SendingRequestException;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

import java.net.SocketTimeoutException;

public abstract class BaseSendRequestAsyncTask extends AsyncTask<Object, Void, Void> {

    protected Fragment _fragment;
    @SuppressLint("StaticFieldLeak")
    protected View _root;
    protected IRequest _request;
    protected int _navigationToLoginId;

    protected void prepareObjects(Object... objects) {
        _fragment = (Fragment) objects[0];
        _root = (View) objects[1];
        _request = (IRequest) objects[2];
        setNavigationToLoginId();
    }

    protected abstract void processResponse(PacketData packet, int code) throws InvalidResponseException;

    protected abstract void setNavigationToLoginId();

    @Override
    protected final Void doInBackground(Object... objects) {
        prepareObjects(objects);
        RequestSenderSingleton requestSender = RequestSenderSingleton.getInstance();
        PacketData response;
        int code;
        try {
            response = requestSender.sendRequest(_fragment, _request);
            code = Integer.parseInt(response.getAttribute(PacketAttribute.CODE.getValue()));
            if (code == ResponseCode.NOT_AUTHENTICATED.getValue()) {
                navigateToLoginBecauseNotAuthenticated();
                return null;
            }
        } catch (UserNotAuthenticatedException ex) {
            navigateToLoginBecauseNotAuthenticated();
            return null;
        } catch (SendingRequestException ex) {
            navigateToLoginBecause(ex.getMessage());
            return null;
        } catch (NoResponseException ex) {
            navigateToLoginBecause("Received no response from the server");
            return null;
        } catch (SocketTimeoutException ex) {
            navigateToLoginBecause("Connection timed out");
            return null;
        }
        try {
            if (response.isValid())
                processResponse(response, code);
            else
                throw new InvalidResponseException("Received an invalid response");
        } catch (InvalidResponseException e) {
            Toast.makeText(_fragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void navigateToLoginBecauseNotAuthenticated() {
        SharedPrefs.writeBoolean(SharedPrefs.IS_SECRET_VALID, false);
        navigateToLoginBecause("You are not authenticated");
    }

    protected void navigateToLoginBecause(String reason) {
        Bundle bundle = new Bundle();
        bundle.putString("redirect_reason", reason);
        Bundle currentFragmentBundle = _fragment.getArguments();
        if (currentFragmentBundle != null) {
            String hostname = currentFragmentBundle.getString("hostname");
            bundle.putString("hostname", hostname);
        }
        navigateToFragment(_navigationToLoginId, bundle);
    }

    protected void navigateToFragment(int navigationActionId) {
        runOnUiThread(() -> Navigation.findNavController(_root).navigate(navigationActionId));
    }

    protected void navigateToFragment(int navigationActionId, String toastMessage) {
        runOnUiThread(() -> {
            Toast.makeText(_fragment.getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(_root).navigate(navigationActionId);
        });
    }

    protected void navigateToFragment(int navigationActionId, Bundle bundle) {
        runOnUiThread(() -> Navigation.findNavController(_root).navigate(navigationActionId, bundle));
    }

    protected void runOnUiThread(Runnable action) {
        _fragment.getActivity().runOnUiThread(action);
    }
}
