package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inscribirse_a_evento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscribirse_aevento);

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenMenuPrincipalEstudiante();
            }
        });
    }
    public void reOpenMenuPrincipalEstudiante() {
        Intent intent = new Intent(this, menuPrincipalEstudiante.class);
        startActivity(intent);
    }

}