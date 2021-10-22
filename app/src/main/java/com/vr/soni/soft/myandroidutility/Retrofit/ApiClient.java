package com.vr.soni.soft.myandroidutility.Retrofit;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit googleRetrofit = null;
    private static Retrofit retrofit = null;

    private static Retrofit firebase = null;
    private Context context;

    //Google Map
    public static ApiInterface googleMapClient() {

        if (googleRetrofit == null) {
            googleRetrofit = new Retrofit.Builder()
                    .baseUrl(Apis.GOOGLE_MAP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return googleRetrofit.create(ApiInterface.class);

    }


    public static ApiInterface getInfo() {

        if (retrofit == null) {

            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Apis.MAIN_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


    public static ApiInterface FirebaseNotificationClient() {
        if (firebase == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            firebase = new Retrofit.Builder()
                    .baseUrl(Apis.FIREBASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return firebase.create(ApiInterface.class);
    }


}

