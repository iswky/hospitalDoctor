package com.example.patact.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.patact.MedService;
import com.example.patact.R;
import com.example.patact.patient.Diagnos;
import com.example.patact.patient.Patient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientDetailsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_patient_details);

        // Получаем информацию о пациенте из Intent'а
        Patient patient = getIntent().getParcelableExtra("patient");

        // Отображаем информацию о пациенте
        TextView fioTextView = findViewById(R.id.fioTextView);
        fioTextView.setText("ФИО: " + patient.getFio());

        TextView ageTextView = findViewById(R.id.ageTextView);
        ageTextView.setText("Возраст: " + patient.getAge());

        TextView bedTextView = findViewById(R.id.bedTextView);
        bedTextView.setText("Палата: " + patient.getBed());

        TextView comeTextView = findViewById(R.id.comeTextView);
        comeTextView.setText("Дата поступления(стационар): " + patient.getDateCome());

        TextView fromTextView = findViewById(R.id.fromTextView);
        fromTextView.setText("Дата поступления(отделение): " + patient.getDateFrom());

        TextView doctorTextView = findViewById(R.id.doctorTextView);
        doctorTextView.setText("Лечащий врач: " + patient.getDoctor());

        Button ibButton = findViewById(R.id.ibButton);
        Button diagnosButton = findViewById(R.id.diagnosButton);


        ibButton.setOnClickListener(view -> {
            // Получение visitRootId для выбранного пациента
            String visitRootId = String.valueOf(patient.getVisitRootId());
            // Получение токена из Intent
            String token = getIntent().getStringExtra("token");
            Log.d("TokenDet", token);

            // Создание экземпляра Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://reshenie-soft.ru:5555/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Создание сервиса MedService
            MedService service = retrofit.create(MedService.class);

            String cont_type = "application/json";

            // Отправка запроса на получение диагнозов
            Call<ResponseBody> call = service.getIb("TOKEN " + token, cont_type, visitRootId);
            Log.d("vrId", visitRootId);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            assert response.body() != null;
                            String responseText = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseText);
                            String ibText = jsonObject.getString("text");
                            Intent intent = new Intent(PatientDetailsActivity.this, IbActivity.class);
                            intent.putExtra("ibText", ibText);
                            startActivity(intent);
                        } catch (IOException | JSONException e) {
                            // Обработка ошибки
                            e.printStackTrace();
                        }
                    } else {
                        // Обработка ошибки
                        Toast.makeText(PatientDetailsActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d("Tag", "Error");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    // Обработка ошибки
                    Log.d("Error", "bad");
                }
            });
        });


        diagnosButton.setOnClickListener(view -> {
            // Получение visitRootId для выбранного пациента
            String visitRootId = String.valueOf(patient.getVisitRootId());
            // Получение токена из Intent
            String token = getIntent().getStringExtra("token");
            Log.d("TokenDet", token);

            // Создание экземпляра Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://reshenie-soft.ru:5555/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Создание сервиса MedService
            MedService service = retrofit.create(MedService.class);

            String cont_type = "application/json";

            // Отправка запроса на получение диагнозов
            Call<List<Diagnos>> call = service.getDiagnoses("TOKEN " + token, cont_type, visitRootId);
            Log.d("vrId", visitRootId);
            call.enqueue(new Callback<List<Diagnos>>() {
                @Override
                public void onResponse(@NonNull Call<List<Diagnos>> call, @NonNull Response<List<Diagnos>> response) {
                    if (response.isSuccessful()) {
                        List<Diagnos> diagnosList = response.body();
                        if (diagnosList != null) {
                            Intent intent = new Intent(PatientDetailsActivity.this, DiagnosActivity.class);
                            Gson gson = new Gson();
                            String diagnosListJson = gson.toJson(diagnosList);
                            intent.putExtra("diagnosListJson", diagnosListJson);
                            startActivity(intent);
                        } else {
                            // Обработка ошибки
                            Toast.makeText(PatientDetailsActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // Обработка ошибки
                        Toast.makeText(PatientDetailsActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d("Tag", "Error");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Diagnos>> call, @NonNull Throwable t) {
                    // Обработка ошибки
                    Log.d("Error", "bad");
                }
            });
        });

    }
}
