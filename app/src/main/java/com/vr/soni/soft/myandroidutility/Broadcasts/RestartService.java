package com.vr.soni.soft.myandroidutility.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vr.soni.soft.myandroidutility.Services.TrackingService;

public class RestartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TrackingService.class));


        /**
         * Use In Manifest
         *         <receiver
         *             android:name=".Broadcasts.RestartService"
         *             android:enabled="true"
         *             android:exported="true"
         *             android:label="RestartServiceWhenStopped"
         *             android:permission="android.permission.RECEIVE_BOOT_COMPLETED">
         *             <intent-filter>
         *                 <action android:name="RestartService" />
         *             </intent-filter>
         *         </receiver>*/
    }
}
