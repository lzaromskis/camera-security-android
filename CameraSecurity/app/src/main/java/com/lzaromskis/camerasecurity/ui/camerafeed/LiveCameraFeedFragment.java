package com.lzaromskis.camerasecurity.ui.camerafeed;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

public class LiveCameraFeedFragment extends Fragment {

    private CameraFeedRepeater _feedUpdater;

    public static LiveCameraFeedFragment newInstance() {
        return new LiveCameraFeedFragment();
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live_camera_feed, container, false);
        ImageView imageView = (ImageView)root.findViewById(R.id.feed_camera_view);
        TextView textView = (TextView)root.findViewById(R.id.feed_error_message_text);
        Switch drawZonesSwitch = (Switch)root.findViewById(R.id.feed_draw_zones);
        Switch drawDetectionsSwitch = (Switch)root.findViewById(R.id.feed_draw_detections);

        boolean drawZones = SharedPrefs.readBoolean(SharedPrefs.DRAW_ZONES);
        drawZonesSwitch.setChecked(drawZones);
        drawZonesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> SharedPrefs.writeBoolean(SharedPrefs.DRAW_ZONES, isChecked));

        boolean drawDetections = SharedPrefs.readBoolean(SharedPrefs.DRAW_DETECTIONS);
        drawDetectionsSwitch.setChecked(drawDetections);
        drawDetectionsSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> SharedPrefs.writeBoolean(SharedPrefs.DRAW_DETECTIONS, isChecked)));

        Handler handler = new Handler();
        _feedUpdater = new CameraFeedRepeater(handler, 750, drawZonesSwitch, drawDetectionsSwitch, this, root, null, imageView, textView);
        handler.post(_feedUpdater);
        return root;
    }

    @Override
    public void onDestroyView() {
        if (_feedUpdater != null)
            _feedUpdater.stop();
        super.onDestroyView();
    }
}