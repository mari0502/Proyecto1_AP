package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Modificar_Evento2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_evento2);

        Button btnModificar = (Button) findViewById(R.id.btn_modificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenQR();
            }
        });
    }

    private void OpenQR(){
        Intent intent = new Intent(this, Pantalla_Modificacion_QR.class);
        startActivity(intent);
    }
}