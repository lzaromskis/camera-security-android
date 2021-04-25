package com.lzaromskis.camerasecurity.utility;

import android.content.Context;

public class Converter {

    public static int dp_to_px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
