package com.testapptwo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created on 28.01.2017.
 */

@DatabaseTable(tableName = "image_list")
public class ImageInfo implements Serializable{

    @DatabaseField(generatedId = true)
    private int dbId;

    @SerializedName("date")
    @Expose
    @DatabaseField
    private long date;

    @SerializedName("id")
    @Expose
    @DatabaseField
    private int id;

    @SerializedName("lat")
    @Expose
    @DatabaseField
    private double latitude;

    @SerializedName("lng")
    @Expose
    @DatabaseField
    private double longitude;

    @SerializedName("url")
    @Expose
    @DatabaseField
    private String url;

    public ImageInfo() {
    }

    public long getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageInfo)) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        return url.equals(imageInfo.url);

    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "date='" + date + '\'' +
                ", id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", url='" + url + '\'' +
                '}';
    }
}
