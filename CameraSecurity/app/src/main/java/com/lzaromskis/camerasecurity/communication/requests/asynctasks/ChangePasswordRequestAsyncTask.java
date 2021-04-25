package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;
import com.lzaromskis.camerasecurity.exceptions.InvalidResponseException;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.BaseSendRequestAsyncTask;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

@SuppressLint("StaticFieldLeak")
public class ChangePasswordRequestAsyncTask extends BaseSendRequestAsyncTask {

    TextView _text;
    EditText _currentPassword;
    EditText _newPassword;
    EditText _newPasswordRepeat;
    Button _button;

    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _text = (TextView)objects[3];
        _currentPassword = (EditText)objects[4];
        _newPassword = (EditText)objects[5];
        _newPasswordRepeat = (EditText)objects[6];
        _button = (Button)objects[7];
    }

    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        String message = packet.getAttribute(PacketAttribute.MESSAGE.getValue());
        if (code == ResponseCode.OK.getValue()) {
            SharedPrefs.writeString(SharedPrefs.SECRET, packet.getAttribute(PacketAttribute.SECRET.getValue()));
            runOnUiThread(() -> {
                _currentPassword.getText().clear();
                _currentPassword.setError(null);
                _newPassword.getText().clear();
                _newPassword.setError(null);
                _newPasswordRepeat.getText().clear();
                _newPasswordRepeat.setError(null);
                _button.setEnabled(false);
                _text.setTextColor(Color.GREEN);
                _text.setText(message);
                _text.invalidate();
            });

        } else {
            runOnUiThread(() -> {
                _text.setTextColor(Color.RED);
                _text.setText(message);
                _text.invalidate();
            });
        }
    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_change_password_to_navigation_login;
    }
}
