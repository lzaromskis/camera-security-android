package com.lzaromskis.camerasecurity.utility;

import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;

        BoundingBox other = (BoundingBox)obj;
        if (other == null)
            return false;

        float e = 1e-5f;
        return Math.abs(this._topLeftX - other._topLeftX) < e &&
                Math.abs(this._topLeftY - other._topLeftY) < e &&
                Math.abs(this._bottomRightX - other._bottomRightX) < e &&
                Math.abs(this._bottomRightY - other._bottomRightY) < e;
    }
}
