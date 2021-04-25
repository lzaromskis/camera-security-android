package com.lzaromskis.camerasecurity.ui.monitoredzones;

import android.annotation.SuppressLint;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

public class MonitoredZoneViews {
    private final TableRow _row;
    private final TextView _textView;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final Switch _switch;
    private final ImageButton _deleteButton;

    public MonitoredZoneViews(TableRow row, TextView textView, @SuppressLint("UseSwitchCompatOrMaterialCode") Switch sw, ImageButton deleteButton) {
        _row = row;
        _textView = textView;
        _switch = sw;
        _deleteButton = deleteButton;
    }

    public TableRow getRow() {
        return _row;
    }

    public TextView getTextView() {
        return _textView;
    }

    public Switch getSwitch() {
        return _switch;
    }

    public ImageButton getDeleteButton() {
        return _deleteButton;
    }

}
