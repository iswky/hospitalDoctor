package com.example.patact;

import com.example.patact.dto.ErrorResponseDto;
import com.example.patact.dto.Login2Dto;
import com.example.patact.dto.LoginDto;
import com.example.patact.dto.LoginResponseDto;
import com.example.patact.patient.Diagnos;
import com.google.gson.JsonArray;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MedService {

    @POST("/doctor/api/login")
    Call<ErrorResponseDto> login(@Body LoginDto loginDto);

    @POST("/doctor/api/login")
    Call<LoginResponseDto> login2(@Body Login2Dto login2Dto);

    @GET("doctor/api/docdep/patient")
    Call<JsonArray> getPatients(@Header("Authorization") String authorization, @Header("Accept") String accept, @Query("date") String date);

    @GET("doctor/api/hospitalization/{visitRootId}/diagnos")
    Call<List<Diagnos>> getDiagnoses(@Header("Authorization") String authorization, @Header("Content-Type") String cont_type, @Path("visitRootId") String visitRootId);

    @GET("doctor/api/hospitalization/{visitRootId}/html")
    Call<ResponseBody> getIb(@Header("Authorization") String authorization, @Header("Content-Type") String cont_type, @Path("visitRootId") String visitRootId);
}
