package com.example.patact.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.patact.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button signInButton;
    LinearLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.btnSignIn);
        root = findViewById(R.id.root);

        signInButton.setOnClickListener(v -> showLoginWindow());
    }

    private void showLoginWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вход");
        dialog.setMessage("Чтобы продолжить, вы должны ввести ваш пароль");

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View signInWindow = inflater.inflate(R.layout.signinwindow, null);
        dialog.setView(signInWindow);

        final EditText passwordET = signInWindow.findViewById(R.id.passwordField);

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());
        dialog.setPositiveButton("Войти", (dialogInterface, which) -> {
            String password = passwordET.getText().toString();

            if(TextUtils.isEmpty(password)){
                Snackbar.make(root, "Введите ваш пароль", Snackbar.LENGTH_SHORT).show();
                Log.d("pass", password);
                return;
            } else{
                Log.d("pass", password);
                SharedPreferences sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password", password);
                editor.apply();

                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
        dialog.show();
    }



}
