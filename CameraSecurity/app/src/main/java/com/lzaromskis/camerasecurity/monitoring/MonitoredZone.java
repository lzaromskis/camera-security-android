package com.lzaromskis.camerasecurity.monitoring;

import androidx.annotation.Nullable;

public final class MonitoredZone {
    private final String _name;
    private final BoundingBox _bounds;
    private boolean _active;
    private String[] _labels;

    public MonitoredZone(String name, BoundingBox bounds, String[] labels) {
        _name = name;
        _bounds = bounds;
        _active = false;
        _labels = labels;
    }

    public String getName() {
        return _name;
    }

    public BoundingBox getBounds() {
        return _bounds;
    }

    public String[] getLabels() {
        return _labels;
    }

    public boolean isActive() {
        return _active;
    }

    public void setActive(boolean value) {
        _active = value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof MonitoredZone))
            return false;

        MonitoredZone other = (MonitoredZone)obj;

        return _name.equals(other._name);
    }
}
