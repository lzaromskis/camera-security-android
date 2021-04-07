package com.lzaromskis.camerasecurity.ui.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;
import com.lzaromskis.camerasecurity.helpers.BaseSendRequestAsyncTask;
import com.lzaromskis.camerasecurity.helpers.SharedPrefs;

public class LoginRequestAsyncTask extends BaseSendRequestAsyncTask {

    private boolean _success;
    private int _responseCode;
    @SuppressLint("StaticFieldLeak")
    private TextView _textView;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progressBar;

    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _success = false;
        _responseCode = -1;
        _textView = (TextView) objects[2];
        if (objects.length == 4) {
            _progressBar = (ProgressBar) objects[3];
        }
    }

    @Override
    protected void processResponse(PacketData packet) {
        if (packet.isValid())
        {
            _responseCode = Integer.parseInt(packet.getAttribute(PacketAttribute.CODE.getValue()));
            if (_responseCode == ResponseCode.OK.getValue()) {
                String secret = packet.getAttribute(PacketAttribute.SECRET.getValue());
                SharedPrefs.writeString(SharedPrefs.SECRET, secret);
                SharedPrefs.writeBoolean(SharedPrefs.IS_SECRET_VALID, true);
                _success = true;
            } else if (_responseCode == ResponseCode.BAD_PASSWORD.getValue()) {

            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (!_success) {
            _textView.setTextColor(Color.RED);
            if (_responseCode == -1)
                _textView.setText("Received invalid packet");
            else if (_responseCode == ResponseCode.BAD_PASSWORD.getValue())
                _textView.setText("Invalid password");
            else
                _textView.setText("Received unknown response code");
        }
        else {
            _textView.setTextColor(Color.GREEN);
            _textView.setText("Login successful");
        }
        _textView.postInvalidate();
        if (_progressBar != null) {
            _progressBar.setVisibility(View.GONE);
            _progressBar.postInvalidate();
        }
    }
}
