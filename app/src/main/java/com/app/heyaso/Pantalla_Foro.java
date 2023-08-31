package com.app.heyaso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Pantalla_Foro extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_foro);

        Button button = (Button) findViewById(R.id.evaluarEvento);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenEvaluar();
            }
        });
    }

    public void OpenEvaluar() {
        Intent intent = new Intent(this, Evaluar_Evento.class);
        startActivity(intent);
    }

}
