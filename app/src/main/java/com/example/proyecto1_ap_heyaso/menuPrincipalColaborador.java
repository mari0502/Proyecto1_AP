package com.example.proyecto1_ap_heyaso;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuPrincipalColaborador extends AppCompatActivity {
    private Usuarios usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_colaborador);

        //Obtiene el objeto
        Intent intent = getIntent();
        usuario = (Usuarios) intent.getSerializableExtra("usuarioActual");

        Button button = (Button) findViewById(R.id.btn_salir);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rePantallaInicio();
            }
        });


        Button button2 = (Button) findViewById(R.id.btn_foro);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenForo();
            }
        });

        Button btn_gestionCuentaEst = (Button) findViewById(R.id.btn_adminCuenta);
        btn_gestionCuentaEst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCuentaEst();
            }
        });

        Button btn_gestionCuentaAso = (Button) findViewById(R.id.btn_adminAso);
        btn_gestionCuentaAso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCuentaAso();
            }
        });

        Button adminEvento = (Button) findViewById(R.id.btn_adminEvento);
        adminEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openAdminEvento();}
        });

    }

    public void openAdminEvento() {
        Intent intent = new Intent(this, Administrar_Evento.class);
        startActivity(intent);
    }

    public void rePantallaInicio() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }


    public void OpenForo() {
        Intent intent = new Intent(this, Pantalla_Foro.class);
        intent.putExtra("usuarioActual", usuario);
        startActivity(intent);
    }


    public void openCuentaEst() {
        Intent intent = new Intent(this, Gestion_CuentaEstudiante.class);
        startActivity(intent);
    }

    public void openCuentaAso() {
        Intent intent = new Intent(this, Gestion_CuentaAsociacion.class);
        intent.putExtra("usuarioActual", usuario);
        startActivity(intent);
    }

}