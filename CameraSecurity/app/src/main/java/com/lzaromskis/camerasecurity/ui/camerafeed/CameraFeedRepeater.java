package com.lzaromskis.camerasecurity.ui.camerafeed;

import android.os.Handler;

import com.lzaromskis.camerasecurity.communication.CameraViewUpdater;

public final class CameraFeedRepeater implements Runnable {
    private final Handler _handler;
    private final int _delay;
    private final Object[] _objects;
    private boolean _isRunning;

    public CameraFeedRepeater(Handler handler, int periodInMilis, Object... updaterObjects) {
        _handler = handler;
        _delay = periodInMilis;
        _objects = updaterObjects;
        _isRunning = true;
    }

    @Override
    public void run() {
        if (_isRunning) {
            new CameraFeedRequestAsyncTask().execute(_objects[0], _objects[1], _objects[2], _objects[3]);
            _handler.postDelayed(this, _delay);
        }
    }

    public void stop() {
        _isRunning = false;
    }
}
