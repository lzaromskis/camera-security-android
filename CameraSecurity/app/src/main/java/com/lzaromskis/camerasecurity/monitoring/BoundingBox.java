package com.lzaromskis.camerasecurity.monitoring;

public final class BoundingBox {

    private final float _topLeftX;
    private final float _topLeftY;
    private final float _bottomRightX;
    private final float _bottomRightY;

    public BoundingBox(float topLeftX, float topLeftY, float bottomRightX, float bottomRightY) {
        _topLeftX = topLeftX;
        _topLeftY = topLeftY;
        _bottomRightX = bottomRightX;
        _bottomRightY = bottomRightY;
    }

    public float getTopLeftX() {
        return _topLeftX;
    }

    public float getTopLeftY() {
        return _topLeftY;
    }

    public float getBottomRightX() {
        return _bottomRightX;
    }

    public float getBottomRightY() {
        return _bottomRightY;
    }
}
