package com.lzaromskis.camerasecurity.communication;

import android.os.Handler;

public class CameraViewUpdatedRunnable implements Runnable {

    private CameraViewUpdater _updater;
    private Handler _handler;
    private int _delay;
    private Object[] _objects;
    private boolean _isRunning;

    public CameraViewUpdatedRunnable(CameraViewUpdater updater, Handler handler, int periodInMilis, Object... updaterObjects) {
        _updater = updater;
        _handler = handler;
        _delay = periodInMilis;
        _objects = updaterObjects;
        _isRunning = true;
    }

    @Override
    public void run() {
        if (_isRunning) {
            new CameraViewUpdater().execute(_objects[0], _objects[1]);
            _handler.postDelayed(this, _delay);
        }
    }

    public void stop() {
        _isRunning = false;
    }
}
