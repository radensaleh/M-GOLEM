package com.tubes.mgolem.Rest;

import com.tubes.mgolem.Api.KelasAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseAPI {

    @FormUrlEncoded
    @POST("login")
    Call<Response> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("loginMhs")
    Call<Response> loginMhs(
            @Field("nim") String nim,
            @Field("password") String password
    );


    @GET("kelas")
    Call<List<KelasAPI>> kelas();
}
