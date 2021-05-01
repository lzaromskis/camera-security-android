package com.lzaromskis.camerasecurity.monitoring.serializers;

import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.utility.BoundingBox;
import com.lzaromskis.camerasecurity.utility.exceptions.DeserializationFailedException;

import org.junit.Assert;
import org.junit.Test;


public class MonitoredZoneSerializerTest {
    @Test
    public void test_serialize() {
        // Arrange
        String name = "test_name";
        BoundingBox bounds = new BoundingBox(1, 2, 3, 4);
        String[] labels = new String[] {"label1", "label2"};
        MonitoredZone zone = new MonitoredZone(name, bounds, labels);
        String expected = "test_name!1.0,2.0,3.0,4.0!False!label1,label2";
        MonitoredZoneSerializer serializer = new MonitoredZoneSerializer();

        // Act
        String result = serializer.serialize(zone);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test_deserialize() throws DeserializationFailedException {
        // Arrange
        String name = "test_name";
        BoundingBox bounds = new BoundingBox(1, 2, 3, 4);
        String[] labels = new String[] {"label1", "label2"};
        MonitoredZone expected = new MonitoredZone(name, bounds, labels);
        String data = "test_name!1,2,3,4!False!label1,label2";
        MonitoredZoneSerializer serializer = new MonitoredZoneSerializer();

        // Act
        MonitoredZone result = serializer.deserialize(data);

        // Assert
        Assert.assertEquals(expected.getName(), result.getName());
        Assert.assertEquals(expected.getBounds(), result.getBounds());
        Assert.assertEquals(expected.isActive(), result.isActive());
        Assert.assertArrayEquals(expected.getLabels(), result.getLabels());
    }

    @Test
    public void test_deserialize_throws() {
        // Arrange
        String data = "testname!1,2,3,4!";
        MonitoredZoneSerializer serializer = new MonitoredZoneSerializer();

        // Act
        // Assert
        Assert.assertThrows(DeserializationFailedException.class, () -> {serializer.deserialize(data);});
    }
}
