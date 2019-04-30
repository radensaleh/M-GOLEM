package com.tubes.mgolem.Rest;

import com.tubes.mgolem.Api.KelasAPI;
import com.tubes.mgolem.Api.MahasiswaAPI;
import com.tubes.mgolem.entitas.Peminjaman;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("registrasi")
    Call<Response> registrasi(
            @Field("nama_mhs") String nama_mhs,
            @Field("nim") String nim,
            @Field("password") String password,
            @Field("id_kelas") String id_kelas
    );

    @GET("getPeminjaman")
    Call<List<Peminjaman>> getPeminjaman(
            @Query("status") String status
    );

    @GET("mahasiswa")
    Call<MahasiswaAPI> getMahasiswa(
            @Query("nim") String nim
    );

    @GET("verifikasi")
    Call<Response> verifikasi(
            @Query("id_pinjam") String id_pinjam,
            @Query("username") String username
    );

    @GET("kelas")
    Call<List<KelasAPI>> kelas();

}
