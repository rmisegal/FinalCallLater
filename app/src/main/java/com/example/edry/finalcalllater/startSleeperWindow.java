package com.example.edry.finalcalllater;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by edry on 06/09/2017.
 */

public class startSleeperWindow extends PopUpWindow {

    TimePicker timePicker;


    public startSleeperWindow(Context myContext) {
        super(myContext);
    }

    @Override


    public void setInflator() {
        this.floatyView = ((LayoutInflater) this.myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.start_app_view, interceptorLayout);
    }


    public void setScreenContent() {
        Button confirm = (Button)  floatyView.findViewById(R.id.confirmButton);

        Button ExitButton  = (Button) floatyView.findViewById(R.id.ExitButton);

        timePicker = (TimePicker) floatyView.findViewById(R.id.simpleTimePicker);

        timePicker.setIs24HourView(true);


        confirm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {



                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calSet.set(Calendar.MINUTE, timePicker.getMinute());
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                if(calSet.compareTo(calNow) <= 0){
                    //Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                }

                MyPhoneState SoundUp = new MyPhoneState();

                SoundUp.onCallStateChanged(myContext,0,null);

                Intent newCallOutIntent = new Intent(myContext, SleepModeService.class);

                newCallOutIntent.putExtra("PERIOD",calSet.getTimeInMillis());

                myContext.startService(newCallOutIntent);

                removeView();

            }
        });

        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView();
            }
        });
    }
}