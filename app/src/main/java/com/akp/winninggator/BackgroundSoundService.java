package com.akp.winninggator;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BackgroundSoundService extends Service {
    public static final String ACTION_PAUSE = "com.akp.winninggator.ACTION_PAUSE";
    public static final String ACTION_RESUME = "com.akp.winninggator.ACTION_RESUME";

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize and start the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.mus);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop and release the MediaPlayer
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(ACTION_PAUSE)) {
                    // Pause the music playback
                    mediaPlayer.pause();
                } else if (action.equals(ACTION_RESUME)) {
                    // Resume the music playback
                    mediaPlayer.start();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // Other service methods...

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}