package com.example.doantotnghiep.thongbao;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.doantotnghiep.MainActivity;
import com.example.doantotnghiep.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        thongbao(context);
    }

    private void thongbao(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        String content = "Có kế hoạch tiết kiệm đã hết thời hạn";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel";
            CharSequence name = context.getString(R.string.app_name);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setDescription(content);
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Thông báo kế hoạch tiết kiệm")
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .build();
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(0,notification);
        } else {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Thông báo kế hoạch tiết kiệm")
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .build();
            notificationManager.notify(0,notification);
        }
    }

}
