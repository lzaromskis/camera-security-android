package com.lzaromskis.camerasecurity.communication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.util.Random;

public class AlertClient {
    @SuppressLint("StaticFieldLeak")
    private static AlertClient instance;
    private static final Random rand = new Random();

    private final String _host;
    private final int _port;
    private Context _context;
    private WebSocket _ws;

    public AlertClient(String host, int port, Context context) {
        _host = host;
        _port = port;
        _context = context;

        if (instance != null) {
            instance.StopListening();
        }
        instance = this;
    }

    public void StartListening() {

        @SuppressLint("StaticFieldLeak") AsyncTask<Object, Void, Void> task = new AsyncTask<Object, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Object... objects) {

                WebSocketFactory wsFactory = new WebSocketFactory();
                try {
                    _ws = wsFactory.createSocket("ws://" + _host + ":" + String.valueOf(_port));
                    _ws.setPingInterval(5 * 1000);
                    _ws.connect();
                    _ws.setAutoFlush(true);
                    _ws.sendPing("ping");
                    _ws.addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket websocket, String text) throws Exception {
                            Log.i("AlertClient", "Received message");
                            if (text.startsWith("alert")) {
                                String zoneName = "Unknown zone name";
                                int spaceIndex = text.indexOf(" ");
                                if (spaceIndex != -1 && spaceIndex + 1 != text.length()) {
                                    zoneName = text.substring(spaceIndex + 1);
                                }
                                Log.i("AlertClient", "Websocket received message: " + text);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(_context.getApplicationContext(), "ALERT_CHANNEL")
                                        .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
                                        .setContentTitle("ALERT!")
                                        .setContentText("Detected an object in " + zoneName)
                                        .setPriority(NotificationCompat.PRIORITY_MAX);
                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(_context.getApplicationContext());
                                // notificationId is a unique int for each notification that you must define
                                notificationManager.notify(rand.nextInt(), builder.build());
                            }
                        }
                    });

                } catch (IOException | WebSocketException e) {
                    Log.e("AlertClient", e.getMessage());
                }

                return null;
            }
        }.execute();
    }

    public void StopListening() {
        if (_ws != null) {
            _ws.sendClose();
            _context = null;
            _ws = null;
        }
    }
}
