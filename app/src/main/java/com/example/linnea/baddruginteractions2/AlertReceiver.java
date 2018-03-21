package com.example.linnea.baddruginteractions2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Linnea on 2/24/2018.
 */

public class AlertReceiver extends BroadcastReceiver
{
    // We need to get the med name and the request code from the reminders activity class

    String drugName;

    @Override
    public void onReceive(Context context, Intent intent)
    {

        drugName = RemindersActivity.drug;

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getNotification("Take Your Medicine!", "Remember to take " + drugName + " now!");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
