package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Crear_Actividad extends AppCompatActivity {

    private FirebaseFirestore mfirestore;
    private List<String> idEventos;
    private List<String> encargados;
    private Spinner spinner;
    private Spinner spinner2;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_actividad);

        mfirestore = FirebaseFirestore.getInstance();
        idEventos = new ArrayList<>();
        encargados = new ArrayList<>();
        spinner=findViewById(R.id.spinnerEventos);
        obteneridEventos();
        spinner2=findViewById(R.id.spinnerEncargados);
        obtenerEncargados();
        Button btnAñadirActividad = findViewById(R.id.btn_añadir);

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenAdministrarAgenda();
            }
        });
        btnAñadirActividad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println(spinner.getSelectedItem().toString());
                System.out.println(spinner2.getSelectedItem().toString());
                agregarActividad();

            }
        });
    }
    private void agregarActividad() {

        TextInputEditText InputIdActividad= findViewById(R.id.IdActividad);;
        Spinner Eventos=findViewById(R.id.spinnerEventos);
        TextInputEditText InputCapacidad = findViewById(R.id.capacidad);
        TextInputEditText InputDescripcion = findViewById(R.id.descripcion);
        TextInputEditText InputDuracion = findViewById(R.id.duracion);
        Spinner Encargado=findViewById(R.id.spinnerEncargados);
        TextInputEditText InputTitulo = findViewById(R.id.titulo);

        String idActividad = InputIdActividad.getText().toString();
        String idEvento = Eventos.getSelectedItem().toString();
        String capacidad = InputCapacidad.getText().toString();
        String descripcion = InputDescripcion.getText().toString();
        String duracion = InputDuracion.getText().toString();
        String encargado = Encargado.getSelectedItem().toString();
        String titulo = InputTitulo.getText().toString();
        String NewidActividad="Act"+idActividad;


        if (TextUtils.isEmpty(idActividad) || TextUtils.isEmpty(capacidad) || TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(duracion) || TextUtils.isEmpty(titulo)) {
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            CollectionReference ActividadRef = mfirestore.collection("Actividad");
            // Verificar si ya existe una actividad con el mismo id en el mismo evento
            ActividadRef.whereEqualTo("idEvento", idEvento)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                boolean numeroActExistente = false;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String IdActividadExist = document.getString("idActividad");
                                    String IdEventoExist = document.getString("idEvento");
                                    if (IdActividadExist.equals(NewidActividad)&&IdEventoExist.equals(idEvento)) {
                                        numeroActExistente = true;
                                        break;
                                    }

                                }

                                if (numeroActExistente) {
                                    Toast.makeText(Crear_Actividad.this, "Ya existe una actividad con este id en el evento", Toast.LENGTH_SHORT).show(); //si es evento1 act 1
                                } else {
                                    ActividadRef.whereEqualTo("idActividad", NewidActividad)
                                                .whereEqualTo("idEvento",idEvento)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                @Override
                                                public void onComplete(Task<QuerySnapshot> task) {

                                                    if (task.isSuccessful()) {
                                                        boolean numeroActExistente = !task.getResult().isEmpty();

                                                        if (numeroActExistente) {
                                                            Toast.makeText(Crear_Actividad.this, "Ya existe una actividad con este id en el evento", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            agregarActividadFirestore(idActividad, idEvento, capacidad, descripcion, duracion, encargado, titulo);
                                                        }
                                                    } else {
                                                        Toast.makeText(Crear_Actividad.this, "Error al verificar la existencia de la actividad", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                    // No existe una actividad con el mismo número, agregar a Firestore

                                };
                            } else {
                                Toast.makeText(Crear_Actividad.this, "Error al verificar la existencia del cubículo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    private void agregarActividadFirestore(String idActividad, String idEvento, String capacidad, String descripcion, String duracion, String encargado,  String titulo){
        CollectionReference ActividadCollection = mfirestore.collection("Actividad");
        String IdActividad= "Act"+idActividad;
        Actividad actividad = new Actividad( IdActividad,  idEvento,  capacidad,  descripcion,  duracion,  encargado, titulo);
        ActividadCollection.add(actividad)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Crear_Actividad.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Crear_Actividad.this, "Failed to upload data", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                });
        reOpenAdministrarAgenda();
    }

    public void reOpenAdministrarAgenda() {
        Intent intent = new Intent(this, AdministrarAgenda.class);
        startActivity(intent);
    }

    private void obteneridEventos(){
        idEventos.add("Seleccione una opción");
        mfirestore.collection("Evento").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documento : queryDocumentSnapshots){
                    idEventos.add(documento.getString("idEvento"));
                }
                spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, idEventos));
            }
        });
    }
    private void obtenerEncargados() {
        encargados.add("Seleccione una opción");
        mfirestore.collection("usuario")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String nombreEncargado = document.getString("nombre");
                                String idTipo = document.getString("idTipo");
                                if (idTipo.equals("Admin")) {
                                    encargados.add(nombreEncargado);
                                }
                            }
                            if (encargados.isEmpty()){
                                return;
                            }
                            else{
                                spinner2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, encargados));
                            }

                        } else {
                            Log.e("Firestore", "Error al obtener los datos", task.getException());
                        }
                        return;
                    }
                });
    }
}