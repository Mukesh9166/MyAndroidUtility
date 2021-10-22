package com.vr.soni.soft.myandroidutility.OtherUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkState
{
    public static boolean isConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info != null)
        {
            if (info.isConnected())
                return true;
        }
        return false;
    }
}
