package com.testapptwo.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 27.01.2017.
 */

public class ImageRequestInfo {

    @SerializedName("base64Image")
    @Expose
    private String base64Image;

    @SerializedName("date")
    @Expose
    private long date;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    public ImageRequestInfo(String base64Image, long date, double lat, double lng) {
        this.base64Image = base64Image;
        this.date = date;
        this.lat = String.valueOf(lat);
        this.lng = String.valueOf(lng);
    }

    public String getBase64Image() {
        return base64Image;
    }

    public long getDate() {
        return date;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "ImageRequestInfo{" +
                "date=" + date +
                ", lat=" + lat +
                ", lng=" + lng +
                ", base64Image.length='" + base64Image.length() + '\'' +
                '}';
    }
}
