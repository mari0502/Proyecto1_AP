package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Gestion_CuentaAsociacion extends AppCompatActivity {
    private Button btn_modAso, btn_adminColab, btn_back;
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

        btn_modAso = (Button) findViewById(R.id.btn_editarCuenta4);
        btn_modAso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Gestion_CuentaAsociacion.this, Modificar_Asociacion.class);
                startActivity(intent);
            }
        });
        btn_back = (Button) findViewById(R.id.btn_volver21);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menuPrincipalColaborador.class);
                startActivity(intent);
            }
        });
    }

    public void openAdminColaborador() {
        Intent intent = new Intent(this, Administrar_Colaboradores.class);
        startActivity(intent);
    }
}