package com.lzaromskis.camerasecurity.ui.camerafeed;

import androidx.lifecycle.ViewModelProvider;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.CameraFeedRequest;
import com.lzaromskis.camerasecurity.communication.requests.IRequest;

public class LiveCameraFeed extends Fragment {

    private LiveCameraFeedViewModel mViewModel;

    private CameraFeedRepeater _feedUpdater;

    public static LiveCameraFeed newInstance() {
        return new LiveCameraFeed();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live_camera_feed, container, false);
        ImageView imageView = (ImageView)root.findViewById(R.id.feed_camera_view);
        TextView textView = (TextView)root.findViewById(R.id.feed_error_message_text);

        Handler handler = new Handler();
        IRequest feedRequest = new CameraFeedRequest();
        _feedUpdater = new CameraFeedRepeater(handler, 750, this, feedRequest, imageView, textView);
        handler.post(_feedUpdater);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LiveCameraFeedViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        if (_feedUpdater != null)
            _feedUpdater.stop();
        super.onDestroyView();
    }
}