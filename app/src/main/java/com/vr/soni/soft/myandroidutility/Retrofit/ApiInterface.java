package com.vr.soni.soft.myandroidutility.Retrofit;

import com.google.gson.JsonObject;
import com.vr.soni.soft.myandroidutility.Models.RequestNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(Apis.GOOGLE_ADDRESS)
    Call<JsonObject> getLocationAddress(@Query("latlng") String latlng,
                                        @Query("key") String key);

    @Headers({"Authorization: key=xyz", "Content-Type:application/json"})
    @POST(Apis.FCM_SEND)
    Call<JsonObject> fcmSend(@Body RequestNotification.RequestNotificaton requestNotificaton);


}
