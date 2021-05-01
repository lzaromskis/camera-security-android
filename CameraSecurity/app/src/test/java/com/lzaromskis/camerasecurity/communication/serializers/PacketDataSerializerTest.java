package com.lzaromskis.camerasecurity.communication.serializers;

import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;

import org.junit.Assert;
import org.junit.Test;


public class PacketDataSerializerTest {
    @Test
    public void test_serialize() {
        // Arrange
        PacketData packet = new PacketData();
        packet.addAttribute(PacketAttribute.CODE.getValue(), "10");
        packet.addAttribute(PacketAttribute.MESSAGE.getValue(), "Test_Message");
        String expected = "message=Test_Message;code=10;";
        PacketDataSerializer serializer = new PacketDataSerializer();

        // Act
        String result = serializer.serialize(packet);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test_deserialize() {
        // Arrange
        String data = "message=Test_Message;code=10;";
        PacketDataSerializer serializer = new PacketDataSerializer();

        // Act
        PacketData result = serializer.deserialize(data);

        // Assert
        Assert.assertEquals("10", result.getAttribute(PacketAttribute.CODE.getValue()));
        Assert.assertEquals("Test_Message", result.getAttribute(PacketAttribute.MESSAGE.getValue()));
    }
}
