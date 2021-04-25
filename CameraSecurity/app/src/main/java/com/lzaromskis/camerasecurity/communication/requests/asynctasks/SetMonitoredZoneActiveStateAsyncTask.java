package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.widget.Toast;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;
import com.lzaromskis.camerasecurity.utility.exceptions.InvalidResponseException;

public class SetMonitoredZoneActiveStateAsyncTask extends BaseSendRequestAsyncTask {
    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        code = Integer.parseInt(packet.getAttribute(PacketAttribute.CODE.getValue()));
        if (code != ResponseCode.OK.getValue()) {
            Toast.makeText(_root.getContext(), "Failed to change activity state. Reloading page...", Toast.LENGTH_LONG).show();
            navigateToFragment(R.id.action_navigation_monitored_zones_self);
        }
    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_monitored_zones_to_navigation_login;
    }
}
