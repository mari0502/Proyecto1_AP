package com.app.heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class Crear_Evento extends AppCompatActivity {

    Button btn_crear;
    EditText titulo, descripcion, lugar, duracion, fecha, categoria, requisitos;
    private FirebaseFirestone mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        mfirestore = FirebaseFirestore.getInstance();

        titulo = findViewById(R.id.titulo);
        descripcion = findViewById(R.id.descripcion);
        lugar = findViewById(R.id.lugar);
        duracion = findViewById(R.id.duracion);
        fecha = findViewById(R.id.fecha);
        categoria = findViewById(R.id.categoria);
        requisitos = findViewById(R.id.requisitos);
        btn_crear = findViewById(R.id.btn_crear);

        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloEvento = titulo.getText().toString().trim();
                String descripcionEvento = descripcion.getText().toString().trim();
                String lugarEvento = lugar.getText().toString().trim();
                String duracionEvento = duracion.getText().toString().trim();
                String fechaEvento = fecha.getText().toString().trim();
                String categoriaEvento = categoria.getText().toString().trim();
                String requisitosEvento = requisitos.getText().toString().trim();

                //se validan si está vacio etc etc


            }
        });
    }
}