package com.lzaromskis.camerasecurity.utility.serializers;

import com.lzaromskis.camerasecurity.utility.BoundingBox;
import com.lzaromskis.camerasecurity.utility.exceptions.DeserializationFailedException;

import org.junit.Assert;
import org.junit.Test;


public class BoundingBoxSerializerTest {
    @Test
    public void test_serialize() {
        // Arrange
        BoundingBox bounds = new BoundingBox(1, 2, 3, 4);
        String expected = "1.0,2.0,3.0,4.0";
        BoundingBoxSerializer serializer = new BoundingBoxSerializer();

        // Act
        String result = serializer.serialize(bounds);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test_deserialize() throws DeserializationFailedException {
        // Arrange
        String data = "1,2,3,4";
        BoundingBox expected = new BoundingBox(1, 2, 3, 4);
        BoundingBoxSerializer serializer = new BoundingBoxSerializer();

        // Act
        BoundingBox result = serializer.deserialize(data);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test_deserialize_throws() {
        // Arrange
        String data = "1,2";
        BoundingBoxSerializer serializer = new BoundingBoxSerializer();

        // Act
        // Assert
        Assert.assertThrows(DeserializationFailedException.class, () -> {serializer.deserialize(data);});
    }
}
