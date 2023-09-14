package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class Registrar_Estudiante extends AppCompatActivity {

    //Declaracion de variables
    Button btn_register = (Button) findViewById(R.id.btn_crearCuenta);
    Button btn_back = (Button) findViewById(R.id.btn_volver8);
    EditText nombre, carrera, carnet, correo, clave, contacto, descripcion;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estudiante);

        nombre = findViewById(R.id.nombre2);
        carrera = findViewById(R.id.carrera);
        carnet = findViewById(R.id.carnet);
        correo = findViewById(R.id.correo3);
        clave = findViewById(R.id.contrasenna);
        contacto = findViewById(R.id.contacto2);
        descripcion = findViewById(R.id.descripcion3);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreEst = nombre.getText().toString().trim();
                String carreraEst = carrera.getText().toString().trim();
                String carnetEst = carnet.getText().toString().trim();
                String correoEst = correo.getText().toString().trim();
                String claveEst = clave.getText().toString().trim();
                String contactoEst = contacto.getText().toString().trim();
                String descripcionEst = descripcion.getText().toString().trim();

                if(nombreEst.isEmpty() && carreraEst.isEmpty() && carnetEst.isEmpty()
                && correoEst.isEmpty() && claveEst.isEmpty() && contactoEst.isEmpty()
                && descripcionEst.isEmpty()){
                    Toast.makeText(Registrar_Estudiante.this, "Complete los datos solicitados para el registro.", Toast.LENGTH_SHORT).show();
                } else {
                    registrarEstudiante(nombreEst, carreraEst, carnetEst, correoEst, claveEst, contactoEst, descripcionEst);
                }
            }
        });
        //Regresar a la pantalla principal
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolverPantallaPrincipal();
            }
        });
    }

    //Funciones ir pantallas
    public void OpenVolverPantallaPrincipal() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }

    private void registrarEstudiante(String nombreEst, String carreraEst, String carnetEst,
                                     String correoEst, String claveEst, String contactoEst, String descripcionEst){
        //Poner el tipo de estudiante default

    }
}