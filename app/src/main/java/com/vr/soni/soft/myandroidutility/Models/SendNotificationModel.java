package com.vr.soni.soft.myandroidutility.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendNotificationModel {


    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("urlImageString")
    @Expose
    private String urlImageString;
    @SerializedName("videoLink")
    @Expose
    private String videoLink;
    @SerializedName("key_2")
    @Expose
    private String key2;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("click_action")
    @Expose
    private String clickAction;
    @SerializedName("user_id")
    @Expose
    private Integer userId;



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImageString() {
        return urlImageString;
    }

    public void setUrlImageString(String urlImageString) {
        this.urlImageString = urlImageString;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
