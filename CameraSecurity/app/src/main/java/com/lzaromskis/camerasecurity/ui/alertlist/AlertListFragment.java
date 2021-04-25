package com.lzaromskis.camerasecurity.ui.alertlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.GetLatestAlertsRequest;
import com.lzaromskis.camerasecurity.communication.requests.asynctasks.GetLatestAlertsRequestAsyncTask;

import java.util.ArrayList;

public class AlertListFragment extends Fragment {

    public AlertListFragment() {
        // Required empty public constructor
    }

    public static AlertListFragment newInstance() {
        return new AlertListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_alert_list, container, false);

        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(root.findViewById(R.id.alert_list_alert_1));
        textViews.add(root.findViewById(R.id.alert_list_alert_2));
        textViews.add(root.findViewById(R.id.alert_list_alert_3));
        textViews.add(root.findViewById(R.id.alert_list_alert_4));
        textViews.add(root.findViewById(R.id.alert_list_alert_5));
        textViews.add(root.findViewById(R.id.alert_list_alert_6));
        textViews.add(root.findViewById(R.id.alert_list_alert_7));
        textViews.add(root.findViewById(R.id.alert_list_alert_8));
        textViews.add(root.findViewById(R.id.alert_list_alert_9));
        textViews.add(root.findViewById(R.id.alert_list_alert_10));

        GetLatestAlertsRequest request = new GetLatestAlertsRequest();
        new GetLatestAlertsRequestAsyncTask().execute(this, root, request, textViews);

        return root;
    }
}