package com.example.edry.finalcalllater;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

public class AudioStateReceiver extends BroadcastReceiver {

    private static int LastRingerMode = AudioManager.RINGER_MODE_VIBRATE;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MyTag", "Flow: AudioStateReceiver : onReceive  ");

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        switch (audioManager.getRingerMode()) {

            case AudioManager.RINGER_MODE_SILENT:

                if (!isMyServiceRunning(SleepModeService.class, context)) {
                    StartSleeperWindow Slper = new StartSleeperWindow(context);
                }

                break;

            case AudioManager.RINGER_MODE_NORMAL:

                if (isMyServiceRunning(SleepModeService.class, context) && audioManager.getStreamVolume(AudioManager.STREAM_RING) >= 1) {
                    StopSleeperWindow Slper = new StopSleeperWindow(context, LastRingerMode);
                }

                break;

            case AudioManager.RINGER_MODE_VIBRATE:
                if (!isMyServiceRunning(SleepModeService.class, context)) {
                    StartSleeperWindow Slper = new StartSleeperWindow(context);
                }
                break;

            default:
                break;
        }

        LastRingerMode = audioManager.getRingerMode();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
