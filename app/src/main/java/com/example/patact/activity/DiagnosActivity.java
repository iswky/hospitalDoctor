package com.example.patact.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patact.R;
import com.example.patact.patient.Diagnos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DiagnosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnos);

        // Получение JSON строки с диагнозами из Intent
        String diagnosListJson = getIntent().getStringExtra("diagnosListJson");

        // Преобразование JSON строки обратно в список объектов Diagnos
        Gson gson = new Gson();
        Type type = new TypeToken<List<Diagnos>>() {}.getType();
        List<Diagnos> diagnosList = gson.fromJson(diagnosListJson, type);

        // Вывод данных в соответствующие TextView
        TextView diagnosTextView = findViewById(R.id.diagnosTextView);
        StringBuilder sb = new StringBuilder();
        for (Diagnos diagnos : diagnosList) {
            sb.append("Код диагноза: ").append(diagnos.getDiagCode()).append("\n");
            sb.append("Наименование диагноза: ").append(diagnos.getDiagText()).append("\n");
            sb.append("Дополнительная информация: ").append(diagnos.getText()).append("\n");
            sb.append("Дата постановки даигноза: ").append(diagnos.getDate()).append("\n");
            sb.append("Тип дигноза: ").append(diagnos.getTypeText()).append("\n");
            sb.append("Форма дигноза: ").append(diagnos.getFormText()).append("\n");
            sb.append("\n");
            sb.append("\n");
        }
        diagnosTextView.setText(sb.toString());
    }
}
