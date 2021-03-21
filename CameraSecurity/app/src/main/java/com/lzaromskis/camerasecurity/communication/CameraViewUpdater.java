package com.lzaromskis.camerasecurity.communication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class CameraViewUpdater extends AsyncTask<Object, Void, Void> {
    private ImageView _view;
    private TextView _textView;
    private Bitmap _bitmap;

    @Override
    protected Void doInBackground(Object... objects) {
        _view = (ImageView)objects[0];
        _textView = (TextView)objects[1];
        Client client = new Client("10.0.2.2", 7500);
        String response = client.sendRequest("code&35;secret&secret;");
        if (response == null)
            return null;
        PacketDataSerializer serializer = new PacketDataSerializer();
        PacketData packet = serializer.deserialize(response);
        String imageData = packet.getAttribute("image");
        if (imageData != null)
        {
            try {
                byte[] imageDataRaw = Base64.getDecoder().decode(imageData);
                _bitmap = BitmapFactory.decodeByteArray(imageDataRaw, 0, imageDataRaw.length);
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
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
