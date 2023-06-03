package com.example.patact.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.patact.R;
import com.example.patact.patient.Patient;
import com.example.patact.patient.PatientAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class greenActivity extends AppCompatActivity implements PatientAdapter.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Получение ответа в виде строки JSON (массив JSON)
        String jsonArrayStr = getIntent().getStringExtra("jsonArray");
        JsonArray jsonResponse = JsonParser.parseString(jsonArrayStr).getAsJsonArray();

        // Преобразование строки JSON в список объектов Patient
        Type patientListType = new TypeToken<List<Patient>>() {}.getType();
        List<Patient> patientList = new Gson().fromJson(jsonResponse, patientListType);

        List<Patient> greenList = new ArrayList<>();

        // Проход по всем элементам списка пациентов
        for (Patient patient : patientList) {
            String prof = patient.getProf();
            if (prof != null) {
                // Проверка значения prof и добавление пациента в список yellowList
                if (prof.equals("Удовлетворительное")) {
                    greenList.add(patient);
                }
            }
        }

        // Инициализация адаптера и установка его в RecyclerView
        PatientAdapter patientAdapter = new PatientAdapter(greenList, this);
        recyclerView.setAdapter(patientAdapter);
    }

    @Override
    public void onItemClick(Patient patient) {
        // Действия при нажатии на элемент списка
        // Toast.makeText(this, "Вы выбрали: " + patient.getFio(), Toast.LENGTH_SHORT).show();
        // Получение токена из Intent
        String token = getIntent().getStringExtra("token");
        Intent intent = new Intent(greenActivity.this, PatientDetailsActivity.class);
        intent.putExtra("patient", patient);
        intent.putExtra("token", token);
        Log.d("Token", token);
        startActivity(intent);
    }
}
