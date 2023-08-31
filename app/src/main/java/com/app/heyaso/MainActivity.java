package com.app.heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Gestionar_colabores.class); //Si quieren probar una pantalla cambian el nombre aqui
        startActivity(intent);
        finish();
    }

}