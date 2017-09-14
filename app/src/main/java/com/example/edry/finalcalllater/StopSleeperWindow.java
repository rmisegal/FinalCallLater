package com.example.edry.finalcalllater;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.edry.finalcalllater.Utils.cancelSleepAlarm;

/**
 * Created by edry on 06/09/2017.
 */

public class StopSleeperWindow extends PopUpWindow {

    Button KeepRemainingButton;
    Button ExitButton;
    TextView popupText;

    int lastRingerMode;


    public StopSleeperWindow(Context myContext, int lastRingerMode) {
        super(myContext);
        this.lastRingerMode = lastRingerMode;
    }

    @Override
    public void setInflator() {

        this.floatyView = ((LayoutInflater) this.myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.stop_app_view, interceptorLayout);

    }

    @Override
    public void setScreenContent() {

        KeepRemainingButton = (Button) floatyView.findViewById(R.id.KeepRemainingButton);
        ExitButton = (Button) floatyView.findViewById(R.id.ExitButton);
        popupText = (TextView) floatyView.findViewById(R.id.StopAppText);
        popupText.setText("Stop Sleeper");

        KeepRemainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager am = (AudioManager)myContext.getSystemService(Context.AUDIO_SERVICE);
                am.setRingerMode(lastRingerMode);
                am.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_SHOW_UI);

                removeView();
            }
        });

        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCallOutIntent = new Intent(myContext, SleepModeService.class);
                myContext.stopService(newCallOutIntent);
                cancelSleepAlarm(myContext);
                removeView();
            }
        });

    }
}
