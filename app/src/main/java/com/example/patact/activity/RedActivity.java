package com.example.patact.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
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

public class RedActivity extends AppCompatActivity implements PatientAdapter.OnItemClickListener {

    private List<Patient> redList;
    private PatientAdapter patientAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Получение ответа в виде строки JSON (массив JSON)
        String jsonArrayStr = getIntent().getStringExtra("jsonArray");
        JsonArray jsonResponse = JsonParser.parseString(jsonArrayStr).getAsJsonArray();

        // Преобразование строки JSON в список объектов Patient
        Type patientListType = new TypeToken<List<Patient>>() {}.getType();
        List<Patient> patientList = new Gson().fromJson(jsonResponse, patientListType);

        redList = new ArrayList<>();

        // Проход по всем элементам списка пациентов
        for (Patient patient : patientList) {
            String prof = patient.getProf();
            if (prof != null) {
                // Проверка значения prof и добавление пациента в соответствующий список
                if (prof.equals("Тяжелые")) {
                    redList.add(patient);
                }
            }
        }

        // Инициализация адаптера и установка его в RecyclerView
        patientAdapter = new PatientAdapter(redList, this);
        recyclerView.setAdapter(patientAdapter);

        // Получение ссылки на SearchView
        searchView = findViewById(R.id.searchView);

        // Установка слушателя для обработки событий поиска
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Вызывается при нажатии кнопки поиска на клавиатуре или вводе текста и нажатии кнопки поиска
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Вызывается при изменении текста в поле ввода
                performSearch(newText);
                return false;
            }
        });
    }

    // Метод для выполнения поиска
    private void performSearch(String searchText) {
        // Создание нового списка для результатов поиска
        List<Patient> searchResults = new ArrayList<>();

        // Проход по всем элементам списка пациентов
        for (Patient patient : redList) {
            String patientName = patient.getFio();
            if (patientName != null && patientName.contains(searchText)) {
                // Если имя пациента содержит введенный текст, добавляем его в результаты поиска
                searchResults.add(patient);
            }
        }

        // Обновление списка в адаптере
        patientAdapter.updateList(searchResults);

        // Проверка, есть ли результаты поиска
        if (searchResults.isEmpty()) {
            Toast.makeText(this, "Пациенты не найдены", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Patient patient) {
        // Действия при нажатии на элемент списка
        // Toast.makeText(this, "Вы выбрали: " + patient.getFio(), Toast.LENGTH_SHORT).show();
        // Получение токена из Intent
        String token = getIntent().getStringExtra("token");
        Intent intent = new Intent(RedActivity.this, PatientDetailsActivity.class);
        intent.putExtra("patient", patient);
        intent.putExtra("token", token);
        Log.d("Token", token);
        startActivity(intent);
    }
}
