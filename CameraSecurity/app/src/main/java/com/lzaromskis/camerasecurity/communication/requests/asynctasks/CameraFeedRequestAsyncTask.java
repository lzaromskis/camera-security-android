package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.exceptions.InvalidResponseException;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.BaseSendRequestAsyncTask;

import java.util.Base64;

public final class CameraFeedRequestAsyncTask extends BaseSendRequestAsyncTask {

    @SuppressLint("StaticFieldLeak")
    private ImageView _view;
    @SuppressLint("StaticFieldLeak")
    private TextView _textView;
    private Bitmap _bitmap;

    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _view = (ImageView)objects[3];
        if (objects[4] != null)
            _textView = (TextView)objects[4];
    }

    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        String imageData = packet.getAttribute(PacketAttribute.IMAGE.getValue());
        if (imageData != null)
        {
            try {
                byte[] imageDataRaw = Base64.getDecoder().decode(imageData);
                _bitmap = BitmapFactory.decodeByteArray(imageDataRaw, 0, imageDataRaw.length);
            } catch (Exception ignored) {
            }
        }
        else {
            throw new InvalidResponseException("Packet is missing image data");
        }

    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_camera_feed_to_navigation_login;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            if (_bitmap != null) {
                _view.setImageBitmap(_bitmap);
                _view.invalidate();
            }
        } catch (Exception ex) {
            if (_textView != null) {
                _textView.clearComposingText();
                _textView.setText(ex.getMessage());
                _textView.invalidate();
            }
        }
    }
}
