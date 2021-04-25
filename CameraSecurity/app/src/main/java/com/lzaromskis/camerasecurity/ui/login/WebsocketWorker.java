package com.lzaromskis.camerasecurity.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.lzaromskis.camerasecurity.communication.AlertClient;
import com.lzaromskis.camerasecurity.utility.SharedPrefs;

public class WebsocketWorker extends Worker {
    private final Context _context;

    public WebsocketWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        _context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String hostname = SharedPrefs.readString(SharedPrefs.HOSTNAME);
        AlertClient alertClient = new AlertClient(hostname, 7501, _context);
        alertClient.StartListening();
        return Result.success();
    }
}
