package com.lzaromskis.camerasecurity.communication.requests.asynctasks;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.PacketAttribute;
import com.lzaromskis.camerasecurity.communication.PacketData;
import com.lzaromskis.camerasecurity.utility.exceptions.InvalidResponseException;
import com.lzaromskis.camerasecurity.ui.addmonitoredzone.AddMonitoredZoneFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class GetDataForCreatingZoneRequestAsyncTask extends BaseSendRequestAsyncTask {
    @SuppressLint("StaticFieldLeak")
    private ImageView _view;
    @SuppressLint("StaticFieldLeak")
    private Button _selectLabelsButton;
    private ArrayList<String> _selectedLabels;
    private AddMonitoredZoneFragment _addZoneFragment;
    private ArrayList<String> _allLabels;
    private Bitmap _bitmap;

    @Override
    protected void prepareObjects(Object... objects) {
        super.prepareObjects(objects);
        _view = (ImageView)objects[3];
        _selectLabelsButton = (Button)objects[4];
        _selectedLabels = (ArrayList<String>)objects[5];
        _addZoneFragment = (AddMonitoredZoneFragment)objects[0];
    }

    @Override
    protected void processResponse(PacketData packet, int code) throws InvalidResponseException {
        String imageData = packet.getAttribute(PacketAttribute.IMAGE.getValue());
        if (imageData != null) {
            try {
                byte[] imageDataRaw = Base64.getDecoder().decode(imageData);
                _bitmap = BitmapFactory.decodeByteArray(imageDataRaw, 0, imageDataRaw.length);
            } catch (Exception ignored) {
                navigateToFragment(R.id.action_navigation_add_monitored_zone_to_navigation_monitored_zones, "Failed to decode image data");
                return;
            }
        } else {
            navigateToFragment(R.id.action_navigation_add_monitored_zone_to_navigation_monitored_zones, "Received packet is missing image data");
            return;
        }

        String labels = packet.getAttribute(PacketAttribute.LABELS.getValue());
        String[] labelsList;
        if (labels == null) {
            navigateToFragment(R.id.action_navigation_add_monitored_zone_to_navigation_monitored_zones, "Received packet is missing labels data");
            return;
        } else {
            labelsList = labels.split(",");
            if (labelsList.length == 0) {
                navigateToFragment(R.id.action_navigation_add_monitored_zone_to_navigation_monitored_zones, "Received packet is missing labels data");
                return;
            }
        }
        _allLabels = new ArrayList<>(Arrays.asList(labelsList));
        java.util.Collections.sort(_allLabels);
        _selectLabelsButton.setOnClickListener(v -> {
            ArrayList<String> _tempSelectedLabels = new ArrayList<>(_selectedLabels);
            PopupMenu popup = new PopupMenu(_fragment.getContext(), _selectLabelsButton);
            for (String label: _allLabels) {
                MenuItem item = popup.getMenu().add(label);
                item.setOnMenuItemClickListener((m) -> {
                    String title = m.getTitle().toString();
                    if (_selectedLabels.contains(title)) {
                        _selectedLabels.remove(title);
                    }
                    else {
                        _selectedLabels.add(title);
                    }
                    runOnUiThread(() -> {_addZoneFragment.setAddZoneButtonEnabled();});
                    return true;
                });
                item.setCheckable(true);
                item.setChecked(_tempSelectedLabels.contains(label));
            }
            popup.show();
        });
    }

    @Override
    protected void setNavigationToLoginId() {
        _navigationToLoginId = R.id.action_navigation_add_monitored_zone_to_navigation_login;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            _view.setImageBitmap(_bitmap);
            _view.invalidate();
        } catch (Exception ignored) {
            navigateToFragment(R.id.action_navigation_add_monitored_zone_to_navigation_monitored_zones, "Could not get a valid image");
        }
    }
}
