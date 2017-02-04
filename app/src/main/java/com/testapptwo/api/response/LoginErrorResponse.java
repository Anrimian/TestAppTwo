package com.testapptwo.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 30.01.2017.
 */

public class LoginErrorResponse {
    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("valid")
    @Expose
    private List<InvalidInfo> invalidInfoList = null;

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public List<InvalidInfo> getInvalidInfoList() {
        return invalidInfoList;
    }

    public class InvalidInfo {

        @SerializedName("field")
        @Expose
        private String field;

        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public String getField() {

            return field;
        }
    }

}
