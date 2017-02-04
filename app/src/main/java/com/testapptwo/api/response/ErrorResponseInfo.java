package com.testapptwo.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 30.01.2017.
 */

public class ErrorResponseInfo {
    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

}
