package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.communication.requests.DeleteMonitoredZoneRequest;
import com.lzaromskis.camerasecurity.communication.requests.SetMonitoredZoneActiveStateRequest;
import com.lzaromskis.camerasecurity.utility.exceptions.DeserializationFailedException;
import com.lzaromskis.camerasecurity.utility.exceptions.InvalidResponseException;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZoneCollection;
import com.lzaromskis.camerasecurity.monitoring.serializers.MonitoredZoneCollectionSerializer;
import com.lzaromskis.camerasecurity.ui.monitoredzones.MonitoredZoneViews;

import java.util.ArrayList;
import java.util.Iterator;

public class GetMonitoredZonesAsyncTask extends BaseSendRequestAsyncTask {
    private ArrayList<MonitoredZoneViews> _monitoredZoneViews;

    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _monitoredZoneViews = (ArrayList<MonitoredZoneViews>) objects[3];
    }

    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        String zonesData = packet.getAttribute(PacketAttribute.ZONES.getValue());
        if (zonesData == null)
            throw new InvalidResponseException("The received packet is missing the monitored zones data");

        if (zonesData.equals("NULL"))
            return;

        MonitoredZoneCollectionSerializer serializer = new MonitoredZoneCollectionSerializer();
        MonitoredZoneCollection zones;
        try {
            zones = serializer.deserialize(zonesData);
        } catch (DeserializationFailedException e) {
            throw new InvalidResponseException("The received packet contains invalid monitored zones data");
        }

        runOnUiThread(() -> {
            int i = 0;
            for (Iterator<MonitoredZone> it = zones.getZones(); it.hasNext(); ) {
                MonitoredZone zone = it.next();
                MonitoredZoneViews view = _monitoredZoneViews.get(i);
                String name = zone.getName();
                view.getRow().setVisibility(View.VISIBLE);
                TextView textView = view.getTextView();
                textView.setText(name);
                textView.setOnClickListener((v) -> {
                    PopupMenu popup = new PopupMenu(_fragment.getContext(), textView);
                    for (String label: zone.getLabels()) {
                        MenuItem item = popup.getMenu().add(label);
                        item.setCheckable(false);
                    }
                    popup.show();
                });
                Switch sw = view.getSwitch();
                sw.setChecked(zone.isActive());
                setSwitchText(sw, zone.isActive());
                sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    SetMonitoredZoneActiveStateRequest request = new SetMonitoredZoneActiveStateRequest(name, isChecked);
                    setSwitchText(sw, isChecked);
                    new SetMonitoredZoneActiveStateAsyncTask().execute(_fragment, _root, request);
                });
                view.getDeleteButton().setOnClickListener((View v) -> {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Delete \"" + name + "\"?")
                            .setMessage("Are you sure you want to delete \"" + name + "\" monitored zone?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                DeleteMonitoredZoneRequest request = new DeleteMonitoredZoneRequest(name);
                                new DeleteMonitoredZoneRequestAsyncTask().execute(_fragment, _root, request);
                            })
                            .setNegativeButton(android.R.string.no, ((dialog, which) -> {

                            })).show();
                });

                i++;
                if (i == 5)
                    break;
            }
        });
    }

    private void setSwitchText(Switch sw, boolean isActive) {
        sw.setText(isActive ? R.string.is_active : R.string.is_inactive);
    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_monitored_zones_to_navigation_login;
    }
}
