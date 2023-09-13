package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class Inscribirse_a_evento extends AppCompatActivity {

    private TextInputEditText  nombre, correo;

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

        Button btnInscribirse = (Button) findViewById(R.id.btn_inscribirse);
        btnInscribirse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenQR();
            }
        });

        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.contacto);
    }
    public void reOpenMenuPrincipalEstudiante() {
        Intent intent = new Intent(this, menuPrincipalEstudiante.class);
        startActivity(intent);
    }

    public void OpenQR(){
        Intent intent = new Intent(this, Pantalla_QR.class);
        intent.putExtra("estudiante", nombre.getText().toString());
        intent.putExtra("correo", correo.getText().toString());
        //falta pasar datos
        startActivity(intent);
    }

}