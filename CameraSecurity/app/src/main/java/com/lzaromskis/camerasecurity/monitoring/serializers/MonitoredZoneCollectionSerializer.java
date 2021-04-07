package com.lzaromskis.camerasecurity.monitoring.serializers;

import com.lzaromskis.camerasecurity.exceptions.DeserializationFailedException;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZoneCollection;

import kotlin.NotImplementedError;

public final class MonitoredZoneCollectionSerializer {
    private static final String ZONE_SEPARATOR = "\\?";

    public String serialize(MonitoredZoneCollection data) {
        throw new NotImplementedError("Currently not needed");
    }

    public MonitoredZoneCollection deserialize(String data) throws DeserializationFailedException {
        MonitoredZoneSerializer mzSerializer = new MonitoredZoneSerializer();
        MonitoredZoneCollection collection = new MonitoredZoneCollection();
        String[] splitData = data.split(ZONE_SEPARATOR);
        for (String z : splitData) {
            MonitoredZone zone = mzSerializer.deserialize(z);
            collection.addZone(zone);
        }
        return collection;
    }
}
