package com.lzaromskis.camerasecurity.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.CameraViewUpdatedRunnable;
import com.lzaromskis.camerasecurity.communication.CameraViewUpdater;
import com.lzaromskis.camerasecurity.communication.Client;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.PacketDataSerializer;

import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private CameraViewUpdatedRunnable _repeatTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ImageView view = (ImageView)root.findViewById(R.id.cameraView);
        TextView textView = (TextView)root.findViewById(R.id.exceptionTextView);
        Handler handler = new Handler();
        CameraViewUpdater updater = new CameraViewUpdater();
        //android.os.Debug.waitForDebugger();

        _repeatTask = new CameraViewUpdatedRunnable(updater, handler, 500, view, textView);


        handler.post(_repeatTask);

        return root;
    }

    @Override
    public void onDestroyView() {
        if (_repeatTask != null) {
            _repeatTask.stop();
        }
        super.onDestroyView();
    }
}