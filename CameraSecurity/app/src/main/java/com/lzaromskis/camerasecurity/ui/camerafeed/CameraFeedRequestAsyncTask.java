package com.lzaromskis.camerasecurity.ui.camerafeed;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.helpers.BaseSendRequestAsyncTask;

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
        _view = (ImageView)objects[2];
        _textView = (TextView)objects[3];
    }

    @Override
    protected void processResponse(PacketData packet) {
        String imageData = packet.getAttribute(PacketAttribute.IMAGE.getValue());
        if (imageData != null)
        {
            try {
                byte[] imageDataRaw = Base64.getDecoder().decode(imageData);
                _bitmap = BitmapFactory.decodeByteArray(imageDataRaw, 0, imageDataRaw.length);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            if (_bitmap != null) {
                _view.setImageBitmap(_bitmap);
                _view.invalidate();
            }
        } catch (Exception ex) {
            _textView.clearComposingText();
            _textView.setText(ex.getMessage());
            _textView.invalidate();
        }
    }
}
