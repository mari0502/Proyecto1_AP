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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;


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
                reAdministrarEvento();
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

    public void reAdministrarEvento() {
        Intent intent = new Intent(this, Administrar_Evento.class);
        startActivity(intent);
    }
    private void agregarEvento() {

        TextInputEditText InputId= findViewById(R.id.IdEvento);
        TextInputEditText InputTitulo = findViewById(R.id.titulo);
        TextInputEditText InputDescripcion = findViewById(R.id.descripcion);
        TextInputEditText InputLugar = findViewById(R.id.lugar);
        TextInputEditText InputDuracion = findViewById(R.id.duracion);
        TextInputEditText InputFecha = findViewById(R.id.fecha);
        TextInputEditText InputCategoria = findViewById(R.id.categoria);
        TextInputEditText InputRequisitos = findViewById(R.id.requisitos);
        //Checkbox InputEncuesta = findViewById(R.id.encuesta);


        String IdEvento = InputId.getText().toString();
        String titulo = InputTitulo.getText().toString();
        String descripcion = InputDescripcion.getText().toString();
        String lugar = InputLugar.getText().toString();
        String duracion = InputDuracion.getText().toString();
        String fecha = InputFecha.getText().toString();
        String categoria = InputCategoria.getText().toString();
        String requisitos= InputRequisitos.getText().toString();
        String NewidEvento="Evento"+IdEvento;

        if ( TextUtils.isEmpty(titulo) || TextUtils.isEmpty(IdEvento) ||  TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(lugar) || TextUtils.isEmpty(duracion) || TextUtils.isEmpty(fecha)|| TextUtils.isEmpty(categoria) ) {
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            System.out.println("Voy a seleccionar la coleccion");
            CollectionReference eventoRef = mfirestore.collection("Evento");
            // Verificar si ya existe un evento con el mismo nombre o número

            Query query = eventoRef.whereEqualTo("titulo", titulo);
                    query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                System.out.println("Entre a task is successfull");
                                boolean nombreEventoExistente = false;
                                boolean numeroEventoExistente = false;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    System.out.println("Entre a document query");
                                    String IdEventoExist = document.getString("IdEvento");
                                    String nombreExist = document.getString("titulo");
                                    if (IdEventoExist.equals(NewidEvento)) {
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
                                    eventoRef.whereEqualTo("IdEvento", NewidEvento)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                                @Override
                                                                public void onComplete(Task<QuerySnapshot> task) {

                                                                    if (task.isSuccessful()) {
                                                                        boolean IdEventoExistente = false;
                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                            String IdEventoExist = document.getString("IdEvento");
                                                                            if (IdEventoExist.equals(NewidEvento)) {
                                                                                IdEventoExistente = true;
                                                                                break;
                                                                            }
                                                                        }

                                                                        if (IdEventoExistente) {
                                                                            Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo numero", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            agregarEventoFirestore(IdEvento,categoria, descripcion,duracion,fecha,lugar,requisitos,titulo);
                                                                                }

                                                                    }
                                                                }
                                                            });

                                    // No existe un evento con el mismo nombre o número, agregar a Firestore
                                }
                            } else {
                                Toast.makeText(Crear_Evento.this, "Error al verificar la existencia del evento", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }


    private void agregarEventoFirestore(String idEvento, String titulo, String descripcion,String lugar,String duracion,String fecha,String categoria, String requisitos){
        CollectionReference eventosCollection = mfirestore.collection("Evento");
        String IdEvento= "Evento"+idEvento;
        Evento evento = new Evento(IdEvento,categoria, descripcion,duracion,fecha,lugar,requisitos,titulo);
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
    public void rePrincipalColaborador() {
        Intent intent = new Intent(this, menuPrincipalColaborador.class);
        startActivity(intent);
    }
}