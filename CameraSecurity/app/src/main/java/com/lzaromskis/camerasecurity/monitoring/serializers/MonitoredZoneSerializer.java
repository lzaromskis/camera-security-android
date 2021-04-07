package com.lzaromskis.camerasecurity.monitoring.serializers;

import com.lzaromskis.camerasecurity.exceptions.DeserializationFailedException;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;

public class MonitoredZoneSerializer {
    private static final String DATA_SEPARATOR = "!";

    public String serialize(MonitoredZone data) {
        BoundingBoxSerializer bbSerializer = new BoundingBoxSerializer();
        return String.join(DATA_SEPARATOR,
                data.getName(),
                bbSerializer.serialize(data.getBounds()),
                data.isActive() ? "True" : "False");
    }

    public MonitoredZone deserialize(String data) throws DeserializationFailedException {
        try {
            BoundingBoxSerializer bbSerializer = new BoundingBoxSerializer();
            String[] splitData = data.split(DATA_SEPARATOR);
            MonitoredZone zone = new MonitoredZone(splitData[0], bbSerializer.deserialize(splitData[1]));
            zone.setActive(splitData[2].equals("True"));
            return zone;
        } catch (IndexOutOfBoundsException e) {
            throw new DeserializationFailedException("Failed to deserialize string to a MonitoredZone");
        }
    }
}
