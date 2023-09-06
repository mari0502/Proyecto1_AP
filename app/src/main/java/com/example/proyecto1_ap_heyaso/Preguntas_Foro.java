package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Preguntas_Foro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_foro);

        Button button = (Button) findViewById(R.id.btn_Volver12);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenForo();
            }
        });
    }

    public void reOpenForo() {
        Intent intent = new Intent(this, Pantalla_Foro.class);
        startActivity(intent);
    }
}