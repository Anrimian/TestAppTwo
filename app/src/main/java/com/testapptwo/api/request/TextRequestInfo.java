package com.testapptwo.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 28.01.2017.
 */

public class TextRequestInfo {

    @SerializedName("text")
    @Expose
    private String text;

    public TextRequestInfo(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CommentTextRequest{" +
                "text='" + text + '\'' +
                '}';
    }
}
