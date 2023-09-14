package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Gestion_CuentaAsociacion extends AppCompatActivity {
    private Button btn_modAso, btn_adminColab, btn_EliminarAso, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cuenta_asociacion);

        btn_adminColab = (Button) findViewById(R.id.btn_gestionColaborador);
        btn_adminColab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAdminColaborador();
            }
        });
    }

    public void openAdminColaborador() {
        Intent intent = new Intent(this, Administrar_Colaboradores.class);
        startActivity(intent);
    }
}