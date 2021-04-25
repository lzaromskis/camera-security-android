package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.AlertService;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.BaseSendRequestAsyncTask;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

public class LoginRequestAsyncTask extends BaseSendRequestAsyncTask {

    private boolean _success;
    private int _responseCode;
    @SuppressLint("StaticFieldLeak")
    private TextView _textView;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progressBar;
    @SuppressLint("StaticFieldLeak")
    private EditText _passwordField;

    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _success = false;
        _responseCode = -1;
        _textView = (TextView) objects[3];
        _passwordField = (EditText) objects[4];
        if (objects.length == 6) {
            _progressBar = (ProgressBar) objects[5];
        }
    }

    @Override
    protected void processResponse(PacketData packet, int code) {
        _responseCode = code;
        if (code == ResponseCode.OK.getValue()) {
            String secret = packet.getAttribute(PacketAttribute.SECRET.getValue());
            SharedPrefs.writeString(SharedPrefs.SECRET, secret);
            SharedPrefs.writeBoolean(SharedPrefs.IS_SECRET_VALID, true);
            _success = true;
        }
    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_login_self;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (!_success) {
            _textView.setTextColor(Color.RED);
            _passwordField.setText("");
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

            AlertService service = new AlertService();
            Intent serviceIntent = new Intent(_fragment.getActivity(), service.getClass());
            if (!isMyServiceRunning(service.getClass())) {
                _fragment.getActivity().startService(serviceIntent);
            }

            Navigation.findNavController(_root).navigate(R.id.action_navigation_login_to_navigation_monitored_zones);

        }
        _textView.postInvalidate();
        _passwordField.postInvalidate();
        if (_progressBar != null) {
            _progressBar.setVisibility(View.GONE);
            _progressBar.postInvalidate();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) _fragment.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }
}
