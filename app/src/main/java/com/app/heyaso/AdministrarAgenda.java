package com.app.heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdministrarAgenda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_agenda);

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenOpciones_evento();
            }
        });
    }


    public void reOpenOpciones_evento() {
        Intent intent = new Intent(this, Opciones_Evento.class);
        startActivity(intent);
    }
}