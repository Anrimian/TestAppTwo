package com.testapptwo.api;


import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.api.data.UserInfo;
import com.testapptwo.api.request.ImageRequestInfo;
import com.testapptwo.api.request.LoginRequestInfo;
import com.testapptwo.api.request.TextRequestInfo;
import com.testapptwo.api.response.ResponseInfo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 27.01.2017.
 */

public interface Api {

    @POST("account/signup")
    Observable<ResponseInfo<UserInfo>> signUp(@Body LoginRequestInfo userInfo);

    @POST("account/signin")
    Observable<ResponseInfo<UserInfo>> signIn(@Body LoginRequestInfo userInfo);

    @GET("image")
    Observable<ResponseInfo<List<ImageInfo>>> getImage(@Query("page") int page, @Header("Access-Token") String userToken);

    @POST("image")
    Observable<ResponseInfo<ImageInfo>> postImage(@Body ImageRequestInfo imageRequestInfo, @Header("Access-Token") String userToken);

    @DELETE("image/{imageId}")
    Observable<ResponseInfo<ImageInfo>> deleteImage(@Path("imageId") int imageId, @Header("Access-Token") String userToken);

    @GET("image/{imageId}/comment")
    Observable<ResponseInfo<List<CommentInfo>>> getComment(@Path("imageId") int imageId, @Query("page") int page, @Header("Access-Token") String userToken);

    @POST("image/{imageId}/comment")
    Observable<ResponseInfo<CommentInfo>> postComment(@Path("imageId") int imageId, @Body TextRequestInfo text, @Header("Access-Token") String userToken);

    @DELETE("image/{imageId}/comment/{commentId}")
    Observable<ResponseInfo<CommentInfo>> deleteComment(@Path("imageId") int imageId, @Path("commentId") int commentId, @Header("Access-Token") String userToken);
}
