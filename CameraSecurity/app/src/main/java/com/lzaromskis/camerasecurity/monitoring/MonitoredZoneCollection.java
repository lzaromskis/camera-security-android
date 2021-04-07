package com.lzaromskis.camerasecurity.monitoring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonitoredZoneCollection {
    private final List<MonitoredZone> _monitoredZones;

    public MonitoredZoneCollection() {
        _monitoredZones = new ArrayList<>();
    }

    public Iterator<MonitoredZone> getZones() {
        return _monitoredZones.iterator();
    }

    public MonitoredZone getZone(String name) {
        for (MonitoredZone zone : _monitoredZones) {
            if (zone.getName().equals(name))
                return zone;
        }
        return null;
    }

    public boolean addZone(MonitoredZone zone) {
        if (_monitoredZones.contains(zone))
            return false;
        _monitoredZones.add(zone);
        return true;
    }

    public boolean removeZone(String name) {
        return _monitoredZones.removeIf(x -> x.getName().equals(name));
    }
}
