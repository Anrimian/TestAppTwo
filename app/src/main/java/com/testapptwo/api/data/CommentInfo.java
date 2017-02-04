package com.testapptwo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created on 03.02.2017.
 */

@DatabaseTable(tableName = "images_info_list")
public class CommentInfo {

    public static final String IMAGE_ID = "image_id";

    @DatabaseField(columnName = IMAGE_ID)
    private int imageId;

    @SerializedName("date")
    @Expose
    @DatabaseField
    private long date;

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private int id;

    @SerializedName("text")
    @Expose
    @DatabaseField
    private String text;

    public CommentInfo() {
    }

    public long getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentInfo)) return false;

        CommentInfo that = (CommentInfo) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "date=" + date +
                ", imageId=" + imageId +
                ", id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
