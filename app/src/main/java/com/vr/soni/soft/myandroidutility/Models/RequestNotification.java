package com.vr.soni.soft.myandroidutility.Models;

import com.google.gson.annotations.SerializedName;

public class RequestNotification {

    public static class RequestNotificaton {

        @SerializedName("to") //  "to" changed to token
        private String token;

        @SerializedName("data")
        private SendNotificationModel sendNotificationModel;

        public SendNotificationModel getSendNotificationModel() {
            return sendNotificationModel;
        }

        public void setSendNotificationModel(SendNotificationModel sendNotificationModel) {
            this.sendNotificationModel = sendNotificationModel;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
