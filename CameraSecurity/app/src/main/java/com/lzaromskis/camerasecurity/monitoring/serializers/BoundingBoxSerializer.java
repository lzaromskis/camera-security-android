package com.lzaromskis.camerasecurity.monitoring.serializers;

import com.lzaromskis.camerasecurity.utility.exceptions.DeserializationFailedException;
import com.lzaromskis.camerasecurity.monitoring.BoundingBox;

public class BoundingBoxSerializer {
    private static final String COORDINATE_SEPARATOR = ",";

    public String serialize(BoundingBox data) {
        return String.join(COORDINATE_SEPARATOR,
                String.valueOf(data.getTopLeftX()),
                String.valueOf(data.getTopLeftY()),
                String.valueOf(data.getBottomRightX()),
                String.valueOf(data.getBottomRightY()));
    }

    public BoundingBox deserialize(String data) throws DeserializationFailedException {
        try {
            String[] splitData = data.split(COORDINATE_SEPARATOR);
            return new BoundingBox(Float.parseFloat(splitData[0]),
                    Float.parseFloat(splitData[1]),
                    Float.parseFloat(splitData[2]),
                    Float.parseFloat(splitData[3]));
        }
        catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new DeserializationFailedException("Failed to deserialize string to a BoundingBox");
        }
    }
}
