package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.utility.exceptions.InvalidResponseException;

import java.util.ArrayList;

public class GetLatestAlertsRequestAsyncTask extends BaseSendRequestAsyncTask {

    ArrayList<TextView> _textViews;

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _textViews = (ArrayList<TextView>) objects[3];
    }

    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        String alertData = packet.getAttribute(PacketAttribute.ALERT_LIST.getValue());
        if (alertData == null)
            throw new InvalidResponseException("The packet does not contain alert list data");

        String[] alerts = alertData.split(",");
        int upTo = alerts.length > 10 ? 9 : alerts.length;
        runOnUiThread(() -> {
            for (int i = 0; i < upTo; i++) {
                TextView view = _textViews.get(i);
                String alert = alerts[i];
                String[] times = alert.split("_");
                if (times.length != 3) {
                    continue;
                }
                view.setText(String.format("%s %s.%s", times[0], times[1].replace('-', ':'), times[2]));
                view.setVisibility(View.VISIBLE);
                view.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("alert_name", alert);
                    navigateToFragment(R.id.action_navigation_alert_list_to_navigation_alert_view, bundle);
                });
            }
        });

    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_alert_list_to_navigation_login;
    }
}
