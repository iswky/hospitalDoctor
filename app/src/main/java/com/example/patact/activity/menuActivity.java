package com.example.patact.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.patact.MedService;
import com.example.patact.R;
import com.example.patact.dto.ErrorResponseDto;
import com.example.patact.dto.Login2Dto;
import com.example.patact.dto.LoginDto;
import com.example.patact.dto.LoginResponseDto;
import com.example.patact.patient.Patient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuActivity extends AppCompatActivity {

    private String jsonArray;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Получение экземпляра SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Получение строки из SharedPreferences
        String password = sharedPreferences.getString("password", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://reshenie-soft.ru:5555/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MedService service = retrofit.create(MedService.class);

        LoginDto loginDto = new LoginDto(null, password);
        Call<ErrorResponseDto> call = service.login(loginDto);
        call.enqueue(new Callback<ErrorResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<ErrorResponseDto> call, @NonNull Response<ErrorResponseDto> response) {
                if (response.code() == 400) {
                    try {
                        assert response.errorBody() != null;
                        ErrorResponseDto errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponseDto.class);
                        if (errorResponse != null) {
                            Integer doctorId = errorResponse.doctorId;
                            // Используйте значение doctorId по необходимости
                            Log.d("DoctorId: ", String.valueOf(doctorId));
                            Login2Dto login2Dto = new Login2Dto(doctorId, password);
                            Call<LoginResponseDto> call2 = service.login2(login2Dto);
                            call2.enqueue(new Callback<LoginResponseDto>() {
                                @Override
                                public void onResponse(@NonNull Call<LoginResponseDto> call2, @NonNull Response<LoginResponseDto> response2) {
                                    String responseBody = new Gson().toJson(response2.body());
                                    LoginResponseDto loginResponse = new Gson().fromJson(responseBody, LoginResponseDto.class);
                                    if (response2.isSuccessful()) {
                                        if (loginResponse != null) {
                                            Integer userId = loginResponse.userId;
                                            token = loginResponse.token;
                                            Log.d("User ID: ", String.valueOf(userId));
                                            Log.d("Token: ", token);

                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                            String date = dateFormat.format(new Date());

                                            String acceptHeader = "application/json, text/plain, */*";

                                            Call<JsonArray> call = service.getPatients("TOKEN " + token, acceptHeader, date);
                                            call.enqueue(new Callback<JsonArray>() {
                                                @Override
                                                public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response3) {
                                                    if (response3.isSuccessful()) {
                                                        JsonArray jsonArrayResponse = response3.body();
                                                        // Обработка полученного массива JSON
                                                        if (jsonArrayResponse != null) {
                                                            // jsonArray содержит полученный массив в формате JSON
                                                            Log.d("Result: ", "Success, loading patients activity");
                                                            jsonArray = jsonArrayResponse.toString();
                                                            Log.d("jsonArrayResponse", jsonArray);
                                                        }
                                                    } else {
                                                        // Обработка ошибки третьего запроса
                                                        Toast.makeText(MenuActivity.this, "Error: " + response3.code(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                                                    Toast.makeText(MenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    } else {
                                        // Обработка ошибки второго запроса
                                        Toast.makeText(MenuActivity.this, "Error in second request, check your password!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<LoginResponseDto> call, @NonNull Throwable t) {
                                    Toast.makeText(MenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ErrorResponseDto> call, @NonNull Throwable t) {
                Toast.makeText(MenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startRed(View v) {
        if (jsonArray != null) {
            Intent intent = new Intent(MenuActivity.this, RedActivity.class);
            intent.putExtra("jsonArray", jsonArray); // Используем значение jsonArray из поля класса
            intent.putExtra("token", token);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Patients list is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void startYellow(View v) {
        if (jsonArray != null) {
            Intent intent = new Intent(MenuActivity.this, YellowActivity.class);
            intent.putExtra("jsonArray", jsonArray); // Используем значение jsonArray из поля класса
            intent.putExtra("token", token);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Patients list is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void startGreen(View v) {
        if (jsonArray != null) {
            Intent intent = new Intent(MenuActivity.this, GreenActivity.class);
            intent.putExtra("jsonArray", jsonArray); // Используем значение jsonArray из поля класса
            intent.putExtra("token", token);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Patients list is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
