package com.lzaromskis.camerasecurity.helpers;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.RequestSenderSingleton;
import com.lzaromskis.camerasecurity.communication.requests.IRequest;

public abstract class BaseSendRequestAsyncTask extends AsyncTask<Object, Void, Void> {

    private Fragment _fragment;
    private IRequest _request;

    protected void prepareObjects(Object... objects) {
        _fragment = (Fragment) objects[0];
        _request = (IRequest) objects[1];
    }

    protected abstract void processResponse(PacketData packet);

    @Override
    protected final Void doInBackground(Object... objects) {
        prepareObjects(objects);
        RequestSenderSingleton requestSender = RequestSenderSingleton.getInstance();
        PacketData response = requestSender.sendRequest(_fragment, _request);
        processResponse(response);
        return null;
    }
}
