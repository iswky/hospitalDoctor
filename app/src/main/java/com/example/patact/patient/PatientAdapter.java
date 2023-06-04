package com.example.patact.patient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patact.R;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private List<Patient> patients;
    private List<Patient> filteredPatients;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Patient patient);
    }

    public PatientAdapter(List<Patient> patients, OnItemClickListener listener) {
        this.patients = patients;
        this.filteredPatients = new ArrayList<>(patients);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаем макет для элемента таблицы
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = filteredPatients.get(position);

        // Задаем значения полей пациента в соответствующие элементы пользовательского интерфейса
        holder.patientNameTextView.setText(patient.getFio() != null ? patient.getFio().concat(" | ") : "null | ");
        holder.patientAgeTextView.setText(patient.getAge() != null ? patient.getAge().concat(" | ") : "null | ");
        holder.patientBedTextView.setText(patient.getBed() != null ? patient.getBed() : "null");
        holder.newTabTextView.setText("\n");

        holder.itemView.setOnClickListener(v -> listener.onItemClick(patient));
    }

    @Override
    public int getItemCount() {
        return filteredPatients.size();
    }

    public void updateList(List<Patient> newList) {
        filteredPatients.clear();
        filteredPatients.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientNameTextView;
        TextView patientAgeTextView;
        TextView patientBedTextView;
        TextView newTabTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientNameTextView = itemView.findViewById(R.id.patient_name_text_view);
            patientAgeTextView = itemView.findViewById(R.id.patient_age_text_view);
            patientBedTextView = itemView.findViewById(R.id.patient_bed_text_view);
            newTabTextView = itemView.findViewById(R.id.patient_new_tab);
        }
    }
}
