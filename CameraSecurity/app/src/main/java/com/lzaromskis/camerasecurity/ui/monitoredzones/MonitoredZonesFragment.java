package com.lzaromskis.camerasecurity.ui.monitoredzones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import com.lzaromskis.camerasecurity.R;
import com.lzaromskis.camerasecurity.helpers.Converter;
import com.lzaromskis.camerasecurity.monitoring.BoundingBox;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZone;
import com.lzaromskis.camerasecurity.monitoring.MonitoredZoneCollection;

import java.util.Iterator;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonitoredZonesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonitoredZonesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonitoredZonesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonitoredZonesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonitoredZonesFragment newInstance(String param1, String param2) {
        MonitoredZonesFragment fragment = new MonitoredZonesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_monitored_zones, container, false);
        Context context = root.getContext();

        //TODO: Fetch monitored zone list
        MonitoredZoneCollection monitoredZones = new MonitoredZoneCollection();
        monitoredZones.addZone(new MonitoredZone("First zone", new BoundingBox(0, 0, 0, 0)));
        monitoredZones.addZone(new MonitoredZone("Second zone", new BoundingBox(0, 0, 0, 0)));
        monitoredZones.addZone(new MonitoredZone("Third zone", new BoundingBox(0, 0, 0, 0)));
        monitoredZones.addZone(new MonitoredZone("Fourth zone", new BoundingBox(0, 0, 0, 0)));
        monitoredZones.addZone(new MonitoredZone("Fifth zone", new BoundingBox(0, 0, 0, 0)));

        MonitoredZone third = monitoredZones.getZone("Third zone");
        third.setActive(true);

        ListView listView = (ListView) root.findViewById(R.id.zones_scrollview);
        //ListView parent = (ListView) listView.getParent();
        //int index = parent.indexOfChild(listView);

        //context = listView.getContext();
        LinearLayout newListView = new LinearLayout(context);
        newListView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        newListView.setOrientation(LinearLayout.VERTICAL);


        int rowHeight = Converter.dp_to_px(context, 60f);
        int rowPadding = Converter.dp_to_px(context, 5f);

        int i = 0;
        for (Iterator<MonitoredZone> it = monitoredZones.getZones(); it.hasNext(); i++) {
            MonitoredZone zone = it.next();
            String zoneName = zone.getName();

            // Create row
            TableRow row = new TableRow(context);
            row.setTag(zoneName + "_row");
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
            rowParams.width = TableRow.LayoutParams.MATCH_PARENT;
            rowParams.height = rowHeight;
            row.setLayoutParams(rowParams);
            row.setPadding(rowPadding, rowPadding, rowPadding, rowPadding);
            if (i % 2 == 0)
                row.setBackgroundColor(0xFCFCFC);
            else
                row.setBackgroundColor(0xF0F0F0);

            // Create text view
            TextView textView = new TextView(context);
            textView.setTag(zoneName + "_text");
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 0.55f
            );
            textView.setLayoutParams(textParams);
            textView.setGravity(Gravity.CENTER | Gravity.START);
            textView.setText(zoneName);
            textView.setTextColor(0x000000);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            textView.setTypeface(Typeface.DEFAULT_BOLD);

            // Create switch
            Switch switchControl = new Switch(context);
            switchControl.setTag(zoneName + "_switch");
            LinearLayout.LayoutParams switchParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 0.325f
            );
            switchControl.setLayoutParams(switchParams);
            switchControl.setText("Is active");
            switchControl.setTextColor(0x1F1F1F);
            switchControl.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            switchControl.setGravity(Gravity.CENTER);
            switchControl.setChecked(zone.isActive());

            // Create image button
            ImageButton button = new ImageButton(context);
            button.setTag(zoneName + "_button");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 0.125f
            );
            button.setLayoutParams(buttonParams);
            button.setImageResource(R.drawable.ic_home_black_24dp);    // 0x0108001d is ID of android:drawable/ic_delete
            button.setOnClickListener((View v) -> {});

            // Assign everything
            row.addView(textView);
            row.addView(switchControl);
            row.addView(button);
            //listView.addFooterView(row);
        }
        //parent.removeView(listView);
        //parent.addView(newListView);
        //listView.deferNotifyDataSetChanged();
        root.invalidate();
        return root;
    }
}