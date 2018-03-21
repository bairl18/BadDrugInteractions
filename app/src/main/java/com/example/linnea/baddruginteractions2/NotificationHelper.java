package com.example.linnea.baddruginteractions2;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Linnea on 2/24/2018.
 */

public class NotificationHelper extends ContextWrapper
{
    private NotificationManager manager;
    public static final String id = "id";

    public NotificationHelper(Context base)
    {
        super(base);
    }

    public NotificationManager getManager()
    {
        if(manager == null)
        {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getNotification (String title, String message)
    {
        int color = ContextCompat.getColor(this, R.color.colorAccent);

        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.icon)
                .setColor(color)
                .setLights(Color.CYAN, 2000, 1000);
    }




}
