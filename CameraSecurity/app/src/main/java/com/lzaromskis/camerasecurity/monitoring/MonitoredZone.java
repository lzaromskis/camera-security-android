package com.lzaromskis.camerasecurity.monitoring;

import androidx.annotation.Nullable;

public final class MonitoredZone {
    private final String _name;
    private final BoundingBox _bounds;
    private boolean _active;

    public MonitoredZone(String name, BoundingBox bounds) {
        _name = name;
        _bounds = bounds;
        _active = false;
    }

    public String getName() {
        return _name;
    }

    public BoundingBox getBounds() {
        return _bounds;
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
