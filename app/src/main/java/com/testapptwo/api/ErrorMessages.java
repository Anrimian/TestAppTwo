package com.testapptwo.api;

import android.util.Log;

import com.testapptwo.R;
import com.testapptwo.api.response.ErrorResponseInfo;
import com.testapptwo.api.response.LoginErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created on 30.01.2017.
 */

public class ErrorMessages {
    public static final String INCORRECT_LOGIN_OR_PASSWORD = "security.signin.incorrect";
    public static final String VALIDATION_ERROR = "validation-error";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";

    public static final HashMap<String, Integer> MESSAGES = new HashMap<>();

    static {
        MESSAGES.put(INCORRECT_LOGIN_OR_PASSWORD, R.string.incorrect_login_or_password);
        MESSAGES.put(PASSWORD, R.string.incorrect_password);
        MESSAGES.put(LOGIN, R.string.incorrect_login);
    }

    public static String selectErrorMessage(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ResponseBody body = httpException.response().errorBody();

            Converter<ResponseBody, ErrorResponseInfo> errorConverter =
                    ApiWrapper.getRetrofit().responseBodyConverter(ErrorResponseInfo.class, new Annotation[0]);
            try {
                ErrorResponseInfo loginErrorResponse = errorConverter.convert(body);
                String serverMessage = loginErrorResponse.getError();
                if (serverMessage != null) {
                    return serverMessage;
                } else {
                    return "Неизвестный ответ сервера";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Неизвестная ошибка";
        } else {
            return "Ошибка при соединении с сервером";
        }
    }

    public static int selectErrorMessageId(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ResponseBody body = httpException.response().errorBody();

            Converter<ResponseBody, LoginErrorResponse> errorConverter =
                    ApiWrapper.getRetrofit().responseBodyConverter(LoginErrorResponse.class, new Annotation[0]);
            try {
                LoginErrorResponse loginErrorResponse = errorConverter.convert(body);
                String serverMessage = getServerErrorMessage(loginErrorResponse);
                if (serverMessage != null) {
                    Integer messageId = ErrorMessages.MESSAGES.get(serverMessage);
                    if (messageId != null) {
                        return messageId;
                    } else {
                        Log.e(ErrorMessages.class.getSimpleName(), "unknown server message: " + serverMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return R.string.error;
        } else {
            return R.string.connection_error;
        }
    }

    private static String getServerErrorMessage(LoginErrorResponse response) {
        String message = response.getError();
        if (message.equals(ErrorMessages.VALIDATION_ERROR)) {
            return response.getInvalidInfoList().get(0).getField();
        }
        return message;
    }
}
