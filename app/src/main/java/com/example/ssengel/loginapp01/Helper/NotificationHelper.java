package com.example.ssengel.loginapp01.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.example.ssengel.loginapp01.Activity.MainActivity;
import com.example.ssengel.loginapp01.Activity.NotificationActivity;
import com.example.ssengel.loginapp01.R;

/**
 * Created by ssengel on 28.04.2018.
 */

public class NotificationHelper extends ContextWrapper {
    private static final String ChannelID = "channelID";
    private static final String ChannelName = "ssengel Channel";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    private void createChannels(){
        NotificationChannel ssengelChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            ssengelChannel = new NotificationChannel(ChannelID,ChannelName, NotificationManager.IMPORTANCE_DEFAULT);
            ssengelChannel.enableLights(true);
            ssengelChannel.enableVibration(true);
            ssengelChannel.setLightColor(Color.GREEN);
            ssengelChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(ssengelChannel);
        }

    }

    public NotificationManager getManager() {
        if(manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getNotificationBuilder(String title, String body){

        Intent targetIntent = new Intent(this, NotificationActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(targetIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), ChannelID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_menu_share)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }
}
