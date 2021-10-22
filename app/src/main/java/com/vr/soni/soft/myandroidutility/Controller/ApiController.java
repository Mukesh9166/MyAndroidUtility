package com.vr.soni.soft.myandroidutility.Controller;

import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vr.soni.soft.myandroidutility.Models.RequestNotification;
import com.vr.soni.soft.myandroidutility.Models.SendNotificationModel;
import com.vr.soni.soft.myandroidutility.Retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiController {

    String TAG = ApiController.class.getSimpleName();


    /**Get Location address From Google Api*/
    public void getAddressFromGoogleApi(String latlng, String placeKey) {

        ApiClient.googleMapClient().getLocationAddress(latlng, placeKey)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        Log.d(TAG, "getPinAddressResponse: " + new Gson().toJson(response.body()));
                        Log.d(TAG, "getPinAddressCall: " + new Gson().toJson(call.request()));


                        try {

                            String status = response.body().get("status").getAsString();

                            if (status.equals("OK")) {
                                JsonObject jsonObject = response.body();
                                //result array
                                JsonArray arrResult = jsonObject.getAsJsonArray("results");

                                JsonObject objRes = arrResult.get(0).getAsJsonObject();

                                String address = "" + objRes.get("formatted_address").getAsString();

                                String currentAddress1 = "<b>Current Address : </b>" + address;


                                Log.d(TAG, "getAddressFromGoogleApi: " + address);

                            } else {

                            }

                        } catch (Exception e) {


                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Log.d(TAG, "getPinAddressError: " + new Gson().toJson(t.getLocalizedMessage()));
                        Log.d(TAG, "getPinAddressCall: " + new Gson().toJson(call.request()));


                    }
                });

    }

    /**Send push notification*/
    public void sendNotification(String title, String content, String topic) {

        SendNotificationModel sendNotificationModel = new SendNotificationModel();
        sendNotificationModel.setBody(content);
        sendNotificationModel.setTitle(title);
        sendNotificationModel.setClickAction("announcement");

        RequestNotification.RequestNotificaton requestNotificaton = new RequestNotification.RequestNotificaton();
        requestNotificaton.setSendNotificationModel(sendNotificationModel);

        requestNotificaton.setToken(topic);

        Call<JsonObject> call = ApiClient.FirebaseNotificationClient().fcmSend(requestNotificaton);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                Log.d("NOTI", response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }


}
