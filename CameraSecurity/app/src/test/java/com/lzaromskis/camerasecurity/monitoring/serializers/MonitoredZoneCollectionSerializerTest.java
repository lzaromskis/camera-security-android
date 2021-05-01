package com.lzaromskis.camerasecurity.monitoring.serializers;

import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZoneCollection;
import com.lzaromskis.camerasecurity.utility.BoundingBox;
import com.lzaromskis.camerasecurity.utility.exceptions.DeserializationFailedException;

import org.junit.Assert;
import org.junit.Test;

public class MonitoredZoneCollectionSerializerTest {
    @Test
    public void test_deserialize() throws DeserializationFailedException {
        // Arrange
        String name1 = "test_name1";
        BoundingBox bounds1 = new BoundingBox(1, 2, 3, 4);
        String[] labels1 = new String[] {"label1", "label2"};
        String name2 = "test_name2";
        BoundingBox bounds2 = new BoundingBox(4, 5, 6, 7);
        String[] labels2 = new String[] {"label1", "label2"};
        String data = "test_name1!1.0,2.0,3.0,4.0!False!label1,label2?test_name2!4.0,5.0,6.0,7.0!False!label1,label2";
        MonitoredZoneCollectionSerializer serializer = new MonitoredZoneCollectionSerializer();

        // Act
        MonitoredZoneCollection result = serializer.deserialize(data);

        // Assert
        MonitoredZone zone1 = result.getZone(name1);
        Assert.assertEquals(name1, zone1.getName());
        Assert.assertEquals(bounds1, zone1.getBounds());
        Assert.assertArrayEquals(labels1, zone1.getLabels());
        MonitoredZone zone2 = result.getZone(name2);
        Assert.assertEquals(name2, zone2.getName());
        Assert.assertEquals(bounds2, zone2.getBounds());
        Assert.assertArrayEquals(labels2, zone2.getLabels());
    }
}
