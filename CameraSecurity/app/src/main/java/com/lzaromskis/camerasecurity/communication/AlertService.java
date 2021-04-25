package com.lzaromskis.camerasecurity.communication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lzaromskis.camerasecurity.utility.SharedPrefs;

public class AlertService extends Service {

    private AlertClient _alertClient;

    @Override
    public void onCreate() {
        super.onCreate();
        String host = SharedPrefs.readString(SharedPrefs.HOSTNAME);
        _alertClient = new AlertClient(host, 7501, this);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        Intent notificationIntent = new Intent(this, AlertService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this, "ALERT_CHANNEL")
                .setContentTitle("Alert listener")
                .setContentText("Alert listener websocket active")
                .setSmallIcon(androidx.activity.R.drawable.notification_icon_background)
                .setContentIntent(pendingIntent)
                .setTicker("Ticker text")
                .build();
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        _alertClient.StartListening();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _alertClient.StopListening();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, AlertServiceRestarter.class);
        this.sendBroadcast(broadcastIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
