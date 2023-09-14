package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuPrincipalEstudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_estudiante);

        Button button = (Button) findViewById(R.id.btn_salir);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rePantallaInicio();
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_inscripción);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenInscribirse();
            }
        });

        Button button3 = (Button) findViewById(R.id.btn_foro);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenForo();
            }
        });

        Button button4 = (Button) findViewById(R.id.btn_calendario);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenCalendario();
            }
        });

        Button btn_gestionEstudiante = (Button) findViewById(R.id.btn_adminCuenta);
        btn_gestionEstudiante.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openGestionEstudiante();
            }
        });
    }



    public void rePantallaInicio() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }

    public void OpenInscribirse() {
        Intent intent = new Intent(this, Inscribirse_a_evento.class);
        startActivity(intent);
    }

    public void OpenForo() {
        Intent intent = new Intent(this, Pantalla_Foro.class);
        startActivity(intent);
    }

    public void OpenCalendario() {
        Intent intent = new Intent(this, Calendario.class);
        startActivity(intent);
    }

    public void openGestionEstudiante() {
        Intent intent = new Intent(this, Gestion_CuentaEstudiante.class);
        startActivity(intent);
    }

}