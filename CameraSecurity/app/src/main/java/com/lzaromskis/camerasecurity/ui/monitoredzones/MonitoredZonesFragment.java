package com.lzaromskis.camerasecurity.ui.monitoredzones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.communication.requests.GetAllMonitoredZonesRequest;
import com.lzaromskis.camerasecurity.helpers.Converter;
import com.lzaromskis.camerasecurity.monitoring.BoundingBox;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZoneCollection;
import com.lzaromskis.camerasecurity.ui.addmonitoredzone.AddMonitoredZoneFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonitoredZonesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonitoredZonesFragment extends Fragment {

    private final ArrayList<MonitoredZoneViews> monitoredZoneViews = new ArrayList<>();

    public MonitoredZonesFragment() {
        // Required empty public constructor
    }

    public static MonitoredZonesFragment newInstance() {
        return new MonitoredZonesFragment();
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_monitored_zones, container, false);
        Context context = getContext();

        monitoredZoneViews.add(new MonitoredZoneViews(root.findViewById(R.id.zone_row_1),
                root.findViewById(R.id.zone_text_1),
                root.findViewById(R.id.zone_switch_1),
                root.findViewById(R.id.zone_delete_button_1)));
        monitoredZoneViews.add(new MonitoredZoneViews(root.findViewById(R.id.zone_row_2),
                root.findViewById(R.id.zone_text_2),
                root.findViewById(R.id.zone_switch_2),
                root.findViewById(R.id.zone_delete_button_2)));
        monitoredZoneViews.add(new MonitoredZoneViews(root.findViewById(R.id.zone_row_3),
                root.findViewById(R.id.zone_text_3),
                root.findViewById(R.id.zone_switch_3),
                root.findViewById(R.id.zone_delete_button_3)));
        monitoredZoneViews.add(new MonitoredZoneViews(root.findViewById(R.id.zone_row_4),
                root.findViewById(R.id.zone_text_4),
                root.findViewById(R.id.zone_switch_4),
                root.findViewById(R.id.zone_delete_button_4)));
        monitoredZoneViews.add(new MonitoredZoneViews(root.findViewById(R.id.zone_row_5),
                root.findViewById(R.id.zone_text_5),
                root.findViewById(R.id.zone_switch_5),
                root.findViewById(R.id.zone_delete_button_5)));

        FloatingActionButton button = root.findViewById(R.id.zones_add_zone_button);
        button.setOnClickListener((View v) -> {
            if (monitoredZoneViews.get(4).getRow().getVisibility() == View.VISIBLE) {
                Toast.makeText(context, "Can't add more than 5 monitored zones!", Toast.LENGTH_LONG).show();
            } else {
                Navigation.findNavController(root).navigate(R.id.action_navigation_monitored_zones_to_navigation_add_monitored_zone);
            }
        });

        new GetMonitoredZonesAsyncTask().execute(this, root, new GetAllMonitoredZonesRequest(), monitoredZoneViews);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        monitoredZoneViews.clear();
    }

}