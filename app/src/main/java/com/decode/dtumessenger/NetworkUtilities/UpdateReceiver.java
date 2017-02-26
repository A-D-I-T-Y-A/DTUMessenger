package com.decode.dtumessenger.NetworkUtilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateReceiver extends BroadcastReceiver {
    public UpdateReceiver() {
    }

    public static boolean RECEIVER_ACTIVE = false;

    public static final long REPEAT_TIME = 1000 * 5;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("UpdateStarter", "intent recieved");
        RECEIVER_ACTIVE = true;
        Log.d("UpdateStarter","Starting "+RECEIVER_ACTIVE);
        Toast.makeText(context,"Intent Recieved",Toast.LENGTH_LONG).show();
        Intent i = new Intent(context,UpdateService.class);
        //context.startService(i);
        AlarmManager service = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND,3);
        //endingIntent pi = PendingIntent.getBroadcast(context.getApplicationContext(),0,i,0);
        PendingIntent pi2 = PendingIntent.getService(context,6969,i,PendingIntent.FLAG_CANCEL_CURRENT);
        service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME , pi2);
        //service.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi2);
        RECEIVER_ACTIVE = false;
        Log.d("UpdateStarter","Exiting "+ RECEIVER_ACTIVE);
    }
}
