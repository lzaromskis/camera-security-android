package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.widget.Toast;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.responses.ResponseCode;
import com.lzaromskis.camerasecurity.utility.exceptions.InvalidResponseException;

public class DeleteMonitoredZoneRequestAsyncTask extends BaseSendRequestAsyncTask {
    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        code = Integer.parseInt(packet.getAttribute(PacketAttribute.CODE.getValue()));
        if (code == ResponseCode.OK.getValue()) {
            navigateToFragment(R.id.action_navigation_monitored_zones_self);
            return;
        }
        if (code == ResponseCode.BAD_DATA.getValue()) {
            Toast.makeText(_root.getContext(), packet.getAttribute(PacketAttribute.MESSAGE.getValue()), Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_monitored_zones_to_navigation_login;
    }
}
