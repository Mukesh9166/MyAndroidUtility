package com.vr.soni.soft.myandroidutility.Services;


import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vr.soni.soft.myandroidutility.R;

import java.util.ArrayList;
import java.util.List;


public class TrackingService extends Service {
    private TextView tvTravelledKms;
    private WindowManager mWindowManager;
    private View mFloatingView;
    private static final String TAG = "TrackingService";
    String distance = "";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    List<String> arrLatLng = new ArrayList<>();
    String employeeId = "";
    String currentAddress = "";
    String latLngArr = "";
    String lat = "";
    String lng = "";
    String lastLatLng = "";
    int intervalTime = 60000;
    boolean isLocationSending = false;
    Context mContext;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        /**
         * Use In Menifest
         * <service
         *             android:name=".Services.TrackingService"
         *             android:enabled="true"
         *             android:exported="true"
         *             android:stopWithTask="false" />
         * */

        notificationManager();

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        Intent restartServiceTask = new Intent(getApplicationContext(), this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartPendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    private void stopForegroundService() {
        Log.d(TAG, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    public void broadcastIntent() {
        Intent intent = new Intent();
        intent.setAction("com.myApp.Ride.Distance");
//        sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();


        try {
            initView();
        } catch (Exception e) {

        }


        Intent iHeartBeatService = new Intent(TrackingService.this,
                TrackingService.class);
        PendingIntent piHeartBeatService = PendingIntent.getService(this, 0,
                iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piHeartBeatService);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), SystemClock.elapsedRealtime() + 1000, piHeartBeatService);

    }

    private void notificationManager() {

        Intent intent = new Intent(this, TrackingService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? getNotificationChannel(notificationManager) : "";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setContentIntent(pendingIntent);


        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You are on a ride...")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setAutoCancel(false)
                .build();


     /*   NotificationCompat.Builder N = new NotificationCompat.Builder(this, "m77")
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle("Your Ride Has Been Started...")
                .setVibrate(NotificationCompat.DEFAULT_VIBRATE)
                .setAutoCancel(false)
                .setCategory(NotificationCompat.CATEGORY_SERVICE).setAutoCancel(true)
                .build();*/

        notification.flags |= Notification.FLAG_NO_CLEAR;

        startForeground(110, notification);
        preferences = getSharedPreferences("pre", MODE_PRIVATE);
        editor = preferences.edit();

        requestLocationUpdates();
        //updateLocation();

    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mContext = context;
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();

        request.setInterval(5000);
        request.setFastestInterval(50000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());
                    double _speed = location.getSpeed();
                   /* double speed = Utility.roundDecimal(Utility.convertSpeed(_speed), 2);
                    Log.d(TAG, "speed: " + speed);*/
                    Log.d(TAG, "getAccuracy: " + location.getAccuracy());
                    //  Toast.makeText(mContext, "your speed is : " + speed, Toast.LENGTH_SHORT).show();
                    if (/*speed >= 5 &&*/ location.getAccuracy() <= 200) {
                        //  String lastSavedLatLng = preferences.getString(Constants.RIDE_LAT_LNG_ARR, "");

                        //  String latLng = lat + "," + lng + "|";

                        // String lastLatLng = preferences.getString(Constants.LAST_LAT_LNG, "");

                       /* if (!latLng.equals(lastLatLng)) {
                            if (preferences.getBoolean(Constants.IS_ON_RIDE, false)) {
                                editor.putString(Constants.RIDE_LAT_LNG_ARR, lastSavedLatLng + latLng);
                                editor.putString(Constants.LAST_LAT_LNG, latLng);
                                editor.apply();
                            } else {
                                editor.putString(Constants.LAST_LAT_LNG, "");
                                stopForegroundService();
                            }
                        }*/


                        //if (!latLng.equals(lastLatLng)) {
                        //arrLatLng.add(latLng);
                        //  }
                        // lastLatLng = latLng;
                        // String arrString = arrLatLng.toString();
                        //latLngArr = arrString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\|\\,", "|").replaceAll(" ", "");


                        // Log.d(TAG, "requestLocationUpdates:arr: " + preferences.getString(Constants.RIDE_LAT_LNG_ARR, ""));
                    }


                    //getCurrentAddress(location);

                }
            }, null);

        }


    }


    private boolean GPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (preferences.getString(Constants.RIDE_STATUS, "").equals("1")) {
            Intent restartService = new Intent("RestartService");
            sendBroadcast(restartService);
        }*/

        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private String getNotificationChannel(NotificationManager notificationManager) {
        String channelId = "channelid";
        String channelName = getResources().getString(com.vr.soni.soft.myandroidutility.R.string.app_name);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }


    public void initView() {
        //Inflate the floating view layout we created
        //   mFloatingView = LayoutInflater.from(this).inflate(R.layout.locator_float_view, null);
        //   Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        //Add the view to the window.
        int LAYOUT_FLAG = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        //ImageView imageViewLogo = (ImageView) mFloatingView.findViewById(R.id.imageViewLogo);
        ImageView imageViewLogo = null;
        imageViewLogo.setOnClickListener(view -> {
            Intent intent = new Intent();

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(this, TrackingService.class);
            intent.setComponent(cn);
            //  intent.setClassName(this.getPackageName(), this.getPackageName() + ".Activity.TravelConvenienceListActivity");


            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> taskList = activityManager.getRunningTasks(10);

            if (taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(TrackingService.class.getSimpleName())) {
                // Toast.makeText(mContext, "Herre", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "This is last activity in the stack");
            } else {
                startActivity(intent);
            }


        });


        //Drag and move floating view using user's touch action.


        mFloatingView.findViewById(R.id.textviewNotDelete).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                           /* if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }*/
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;

                    case MotionEvent.ACTION_BUTTON_PRESS:
                        Toast.makeText(mContext, "O yeah..", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });


    }




  /*  private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }*/


}
