package com.example.proyecto1_ap_heyaso;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuPrincipalColaborador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_colaborador);

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
    }

    public void rePantallaInicio() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }


    public void OpenForo() {
        Intent intent = new Intent(this, Pantalla_Foro.class);
        startActivity(intent);
    }

}