package com.vr.soni.soft.myandroidutility.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vr.soni.soft.myandroidutility.R;

import java.util.Random;

public class FirebaseNotificationService extends FirebaseMessagingService {
    private NotificationManager notificationManager;


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // startService(new Intent(this, TrackingService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"), remoteMessage.getData().get("click_action"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addNotification(String body, String title, String type) {
   /*     SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        int notiCount = sharedPreferences.getInt(Constants.NOTIFICATION_COUNT, 0);

        editor.putInt(Constants.NOTIFICATION_COUNT, notiCount + 1);
        editor.apply();*/

        /**
         * Use in Menifest
         *  <service
         *             android:name=".Services.FirebaseNotificationService"
         *             android:permission="com.google.android.c2dm.permission.SEND">
         *             <intent-filter>
         *                 <action android:name="com.google.firebase.MESSAGING_EVENT" />
         *                 <action android:name="com.google.android.c2dm.intent.RECEIVE" />
         *             </intent-filter>
         *         </service>
         * */


        createNotificationChannel();
        Intent intent = null;


        /*switch (type) {
            case "1":
            case "request":
                intent = new Intent(this, PendingForApprovalRequestsActivity.class);
                intent.putExtra("requestType", type);
                broadcastIntent("com.myApp.Advance");
                break;
            case "2":
                intent = new Intent(this, LeaveRequestActivity.class);
                break;
            case "3":
                intent = new Intent(this, AttendanceRequestActivity.class);
                break;
            case "4":
                intent = new Intent(this, AnnouncementsActivity.class);
                break;
            case "5":
                intent = new Intent(this, NotificationActivity.class);
                broadcastIntent("com.myApp.CUSTOM_EVENT");
                break;

            default:
                intent = new Intent(this, NotificationActivity.class);
                break;
        }*/

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //   Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.beep);
        NotificationCompat.Builder n = new NotificationCompat.Builder(this, "m77")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
        //.setSound(defaultSoundUri);
        // Set the intent that will fire when the user taps the notification;

        int notificationId = new Random().nextInt(60000);

        notificationManager.notify(notificationId /* ID of notification */, n.build());

    }


    public void broadcastIntent(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
//        sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createNotificationChannel() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

       // Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification_tune);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Hello there";
            String description = "New Message from BLG";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("m77", name, importance);

           // channel.setSound(defaultSoundUri, audioAttributes);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
