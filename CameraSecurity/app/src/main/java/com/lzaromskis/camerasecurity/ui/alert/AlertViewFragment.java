package com.lzaromskis.camerasecurity.ui.alert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.GetAlertImageRequest;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.CameraFeedRequestAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertViewFragment extends Fragment {

    public static AlertViewFragment newInstance() {
        return new AlertViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_alert_view, container, false);

        ImageView imageView = root.findViewById(R.id.alert_view_image);

        Bundle bundle = getArguments();
        String name = bundle.getString("alert_name");
        GetAlertImageRequest request = new GetAlertImageRequest(name);

        new CameraFeedRequestAsyncTask().execute(this, root, request, imageView, null);

        return root;
    }
}