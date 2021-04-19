package com.lzaromskis.camerasecurity.ui.addmonitoredzone;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.lzaromskis.camerasecurity.MainActivity;
import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.AddMonitoredZoneRequest;
import com.lzaromskis.camerasecurity.communication.requests.BaseRequest;
import com.lzaromskis.camerasecurity.communication.requests.CameraFeedRequest;
import com.lzaromskis.camerasecurity.communication.requests.GetDataForCreatingZoneRequest;
import com.lzaromskis.camerasecurity.monitoring.BoundingBox;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.ui.camerafeed.CameraFeedRequestAsyncTask;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMonitoredZoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMonitoredZoneFragment extends Fragment {




    private enum State {
        IDLE,
        SET_TOP_LEFT,
        SET_BOTTOM_RIGHT
    }

    private TextView _topLeftMarker;
    private TextView _bottomRightMarker;
    private ImageView _cameraView;
    private Button _setTopLeftButton;
    private Button _setBottomRightButton;
    private Button _addZoneButton;
    private Button _selectLabelsButton;
    private State _state;
    private EditText _nameInput;

    private ArrayList<String> _selectedLabels;
    private ArrayList<String> _tempSelectedLabels;

    public AddMonitoredZoneFragment() {
        // Required empty public constructor
    }

    public static AddMonitoredZoneFragment newInstance(String param1, String param2) {
        return new AddMonitoredZoneFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_monitored_zone, container, false);

        _cameraView = root.findViewById(R.id.add_zone_image);
        _topLeftMarker = root.findViewById(R.id.top_left_marker);
        _bottomRightMarker = root.findViewById(R.id.bottom_right_marker);
        _state = State.IDLE;

        _setTopLeftButton = root.findViewById(R.id.add_zone_top_left_button);
        _setBottomRightButton = root.findViewById(R.id.add_zone_bottom_right_button);
        _selectLabelsButton = root.findViewById(R.id.add_zone_select_labels);
        _addZoneButton = root.findViewById(R.id.add_zone_add_button);

        _nameInput = root.findViewById(R.id.add_zone_name_input);

        _topLeftMarker.setVisibility(View.INVISIBLE);
        _bottomRightMarker.setVisibility(View.INVISIBLE);

        _selectedLabels = new ArrayList<>();

        BaseRequest getRequest = new GetDataForCreatingZoneRequest();
        new GetDataForCreatingZoneRequestAsyncTask().execute(this, root, getRequest, _cameraView, _selectLabelsButton, _selectedLabels);
        new CameraFeedRequestAsyncTask().execute(this, root, new CameraFeedRequest(false, false), _cameraView, null);

        Context context = getContext();
        _setTopLeftButton.setOnClickListener(v -> {
            _state = State.SET_TOP_LEFT;
            Toast.makeText(context, "Select the top left corner", Toast.LENGTH_SHORT).show();
        });

        _setBottomRightButton.setOnClickListener(v -> {
            _state = State.SET_BOTTOM_RIGHT;
            Toast.makeText(context, "Select the bottom right corner", Toast.LENGTH_SHORT).show();
        });



        _nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setAddZoneButtonEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _addZoneButton.setEnabled(false);
        _addZoneButton.setOnClickListener(v -> {
            float imageWidth = _cameraView.getWidth();
            float imageHeight = _cameraView.getHeight();

            float imageX = _cameraView.getX();
            float imageY = _cameraView.getY();

            float halfMarker = (float)(_topLeftMarker.getWidth() / 2);

            float topLeftX = (_topLeftMarker.getX() + halfMarker - imageX) / imageWidth;
            float topLeftY = (_topLeftMarker.getY() + halfMarker - imageY) / imageHeight;
            float bottomRightX = (_bottomRightMarker.getX() + halfMarker - imageX) / imageWidth;
            float bottomRightY = (_bottomRightMarker.getY() + halfMarker - imageY) / imageHeight;

            String name = _nameInput.getText().toString();
            BoundingBox bounds = new BoundingBox(topLeftX, topLeftY, bottomRightX, bottomRightY);

            MonitoredZone zone = new MonitoredZone(name, bounds, _selectedLabels.toArray(new String[0]));
            AddMonitoredZoneRequest request = new AddMonitoredZoneRequest(zone);
            new AddMonitoredZoneRequestAsyncTask().execute(this, root, request);
        });

        root.setOnTouchListener(this::onTouch);

        return root;
    }

    public void setAddZoneButtonEnabled() {
        _addZoneButton.setEnabled(_topLeftMarker.getVisibility() == View.VISIBLE
                && _bottomRightMarker.getVisibility() == View.VISIBLE
                && _nameInput.getText().length() != 0
                && _selectedLabels.size() != 0);
    }

    private boolean onTouch(View v, MotionEvent event) {
        if (_state == State.IDLE || event.getAction() != MotionEvent.ACTION_DOWN)
            return false;
        else {
            int x = (int)event.getX();
            int y = (int)event.getY();
            if (y >= _setTopLeftButton.getY())
                return false;

            int imageX = (int) _cameraView.getX();
            int imageY = (int) _cameraView.getY();
            int imageWidth = _cameraView.getWidth();
            int imageHeight = _cameraView.getHeight();

            int markerHalf = _topLeftMarker.getWidth() / 2;

            // Clamp positions to fit image
            x = Math.max(x, imageX);
            x = Math.min(x, imageX + imageWidth);
            y = Math.max(y, imageY);
            y = Math.min(y, imageY + imageHeight);
            if (_state == State.SET_TOP_LEFT) {
                if (_bottomRightMarker.getVisibility() == View.VISIBLE) {
                    int markerX = (int)_bottomRightMarker.getX() + markerHalf;
                    int markerY = (int)_bottomRightMarker.getY() + markerHalf;

                    if (x >= markerX && y >= markerY) {
                        Toast.makeText(getContext(), "Top left corner is too low and too much to the right", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else if (x >= markerX) {
                        Toast.makeText(getContext(), "Top left corner is too much to the right", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else if (y >= markerY) {
                        Toast.makeText(getContext(), "Top left corner is too low", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                _topLeftMarker.setX(x - markerHalf);
                _topLeftMarker.setY(y - markerHalf);
                _topLeftMarker.setVisibility(View.VISIBLE);
                _topLeftMarker.invalidate();
                _state = State.IDLE;
            }
            if (_state == State.SET_BOTTOM_RIGHT) {
                if (_topLeftMarker.getVisibility() == View.VISIBLE) {
                    int markerX = (int)_topLeftMarker.getX() + markerHalf;
                    int markerY = (int)_topLeftMarker.getY() + markerHalf;

                    if (x <= markerX && y <= markerY) {
                        Toast.makeText(getContext(), "Bottom right corner is too high and too much to the left", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else if (x <= markerX) {
                        Toast.makeText(getContext(), "Bottom right corner is too much to the left", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else if (y <= markerY) {
                        Toast.makeText(getContext(), "Bottom right corner is too high", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                _bottomRightMarker.setX(x - markerHalf);
                _bottomRightMarker.setY(y - markerHalf);
                _bottomRightMarker.setVisibility(View.VISIBLE);
                _bottomRightMarker.invalidate();
                _state = State.IDLE;
            }

            setAddZoneButtonEnabled();
        }
        return false;
    }


}