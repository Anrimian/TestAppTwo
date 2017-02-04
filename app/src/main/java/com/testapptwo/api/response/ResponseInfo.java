package com.testapptwo.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 04.02.2017.
 */

public class ResponseInfo<T> {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    private T data;

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "data=" + data +
                ", status=" + status +
                '}';
    }
}
