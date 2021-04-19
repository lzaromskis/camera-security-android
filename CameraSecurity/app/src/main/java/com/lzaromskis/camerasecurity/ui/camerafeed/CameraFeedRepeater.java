package com.lzaromskis.camerasecurity.ui.camerafeed;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.Switch;

import com.lzaromskis.camerasecurity.communication.CameraViewUpdater;
import com.lzaromskis.camerasecurity.communication.requests.CameraFeedRequest;

public final class CameraFeedRepeater implements Runnable {
    private final Handler _handler;
    private final int _delay;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final Switch _drawZonesSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final Switch _drawDetectionsSwitch;
    private final Object[] _objects;
    private boolean _isRunning;

    public CameraFeedRepeater(Handler handler, int periodInMilis, Switch drawZonesSwitch, Switch drawDetectionsSwitch, Object... updaterObjects) {
        _handler = handler;
        _delay = periodInMilis;
        _drawZonesSwitch = drawZonesSwitch;
        _drawDetectionsSwitch = drawDetectionsSwitch;
        _objects = updaterObjects;
        _isRunning = true;
    }

    @Override
    public void run() {
        if (_isRunning) {
            boolean drawZones = false;
            boolean drawDetections = false;
            if (_drawZonesSwitch != null)
                drawZones = _drawZonesSwitch.isChecked();
            if (_drawDetectionsSwitch != null)
                drawDetections = _drawDetectionsSwitch.isChecked();
            CameraFeedRequest request = new CameraFeedRequest(drawZones, drawDetections);
            new CameraFeedRequestAsyncTask().execute(_objects[0], _objects[1], request , _objects[3], _objects[4]);
            _handler.postDelayed(this, _delay);
        }
    }

    public void stop() {
        _isRunning = false;
    }
}
