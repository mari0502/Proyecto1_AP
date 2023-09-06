package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class Crear_Evento extends AppCompatActivity {

    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        mfirestore = FirebaseFirestore.getInstance();

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rePrincipalColaborador();
            }
        });

        Button btnCrearEvento = findViewById(R.id.btn_crear);
        btnCrearEvento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Holaa1");
                agregarEvento();

            }
        });
    }

    public void rePrincipalColaborador() {
        Intent intent = new Intent(this, menuPrincipalColaborador.class);
        startActivity(intent);
    }
        private void agregarEvento() {

            TextInputEditText InputId= findViewById(R.id.id);
            TextInputEditText InputTitulo = findViewById(R.id.titulo);
            TextInputEditText InputDescripcion = findViewById(R.id.descripcion);
            TextInputEditText InputLugar = findViewById(R.id.lugar);
            TextInputEditText InputDuracion = findViewById(R.id.duracion);
            TextInputEditText InputFecha = findViewById(R.id.fecha);
            TextInputEditText InputCategoria = findViewById(R.id.categoria);
            TextInputEditText InputRequisitos = findViewById(R.id.requisitos);
            //Checkbox InputEncuesta = findViewById(R.id.encuesta);


            String Identificador = InputId.getText().toString();
            String Titulo = InputTitulo.getText().toString();
            String Descripcion = InputDescripcion.getText().toString();
            String Lugar = InputLugar.getText().toString();
            String Duracion = InputDuracion.getText().toString();
            String Fecha = InputFecha.getText().toString();
            String Categoria = InputCategoria.getText().toString();
            String Requisitos= InputRequisitos.getText().toString();
            String NewidEvento="Evento"+Identificador;

            if ( TextUtils.isEmpty(Titulo) || TextUtils.isEmpty(Identificador) ||  TextUtils.isEmpty(Descripcion) || TextUtils.isEmpty(Lugar) || TextUtils.isEmpty(Duracion) || TextUtils.isEmpty(Fecha)|| TextUtils.isEmpty(Categoria) || TextUtils.isEmpty(Requisitos) )
            {
                Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                System.out.println("Holaa");
                CollectionReference eventoRef = mfirestore.collection("Evento");
                // Verificar si ya existe un evento con el mismo nombre o número
                eventoRef.whereEqualTo("titulo", Titulo)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean nombreEventoExistente = false;
                                    boolean numeroEventoExistente = false;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String idEventoExist = document.getString("idEvento");
                                        String nombreExist = document.getString("titulo");
                                        if (idEventoExist.equals(NewidEvento)) {
                                            numeroEventoExistente = true;
                                            break;
                                        }
                                        nombreEventoExistente = true;
                                    }

                                    if (nombreEventoExistente) {
                                        Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo nombre", Toast.LENGTH_SHORT).show();
                                    } else if (numeroEventoExistente) {
                                        Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo número", Toast.LENGTH_SHORT).show();
                                    } else {
                                        eventoRef.whereEqualTo("idEvento", NewidEvento)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                    @Override
                                                    public void onComplete(Task<QuerySnapshot> task) {

                                                        if (task.isSuccessful()) {
                                                            boolean idEventoExistente = false;
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                String idEventoExist = document.getString("idEvento");
                                                                if (idEventoExist.equals(NewidEvento)) {
                                                                    idEventoExistente = true;
                                                                    break;
                                                                }
                                                            }

                                                            if (idEventoExistente) {
                                                                Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo numero", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                agregarEventoFirestore(Identificador, Titulo, Descripcion, Lugar, Duracion, Fecha, Categoria, Requisitos);
                                                            }

                                                        }
                                                    }
                                                });

                                        // No existe un evento con el mismo nombre o número, agregar a Firestore
                                    };
                                } else {
                                    Toast.makeText(Crear_Evento.this, "Error al verificar la existencia del evento", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

    }


        private void agregarEventoFirestore(String identificador, String titulo, String descripcion,String lugar,String duracion,String fecha,String categoria, String requisitos){
            CollectionReference eventosCollection = mfirestore.collection("Evento");
            String idEvento= "Evento"+identificador;
            Evento evento = new Evento(identificador,titulo, descripcion,lugar,duracion,fecha,categoria,requisitos);
            eventosCollection.add(evento)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Crear_Evento.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Crear_Evento.this, "Failed to upload data", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                    });
            rePrincipalColaborador();
        }

    }