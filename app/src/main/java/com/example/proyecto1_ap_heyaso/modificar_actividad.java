package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;

import java.util.List;


public class modificar_actividad extends AppCompatActivity {
    private FirebaseFirestore mfirestore;
    private List<String> infoActividades;
    private List<String> encargados;
    private Spinner inputEncargados;
    private TextInputEditText inputTitulo;
    private TextInputEditText inputDescripcion;
    private TextInputEditText inputDuracion;
    private ArrayAdapter<String> spinnerEncargadosAdapter;
    private Button btnVolver;
    private Button btnModificar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_actividad);
        mfirestore = FirebaseFirestore.getInstance();
        encargados = new ArrayList<>();
        infoActividades = new ArrayList<>();
        btnVolver = findViewById(R.id.btn_volver);
        btnModificar = findViewById(R.id.btn_modificarA);


        inputEncargados = findViewById(R.id.spinnerEncargados);
        inputTitulo = findViewById(R.id.titulo);
        inputDescripcion = findViewById(R.id.descripcion);
        inputDuracion = findViewById(R.id.duracion);
        spinnerEncargadosAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, encargados);

        Intent intent = getIntent();
        String idEvento = intent.getStringExtra("idEvento");
        String idActividad = intent.getStringExtra("idActividad");


        obtenerEncargados(idEvento);
        getInfoActividades(idEvento, idActividad);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), modificar_actividad1.class);
                startActivity(intent);
            }
        });


        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                modificarActividad(idEvento, idActividad);
                Intent intent = new Intent(getApplicationContext(), AdministrarAgenda.class);
                startActivity(intent);
            }
        });

    }

    private void obtenerEncargados(String idEvento) {
        encargados.clear();
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
                            if (encargados.size() == 1) { // Si solo queda la opción predeterminada, muestra un mensaje.
                                encargados.add("No hay encargados disponibles para esta asociación");
                            }
                            inputEncargados.setAdapter(spinnerEncargadosAdapter);
                        } else {
                            Log.e("Firestore", "Error al obtener los datos", task.getException());
                        }
                    }
                });
    }

    private void getInfoActividades(String idEvento, String idActividad){
        if(idEvento != "Seleccione un evento" && idActividad != "Seleccione una actividad"){
            mfirestore.collection("Actividad").whereEqualTo("idEvento", idEvento)
                    .whereEqualTo("idActividad", idActividad)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot documento : task.getResult()) {
                            infoActividades.add(documento.getString("titulo"));
                            infoActividades.add(documento.getString("descripcion"));
                            infoActividades.add(documento.getString("duracion"));
                            infoActividades.add(documento.getString("encargados"));

                            setCampos();
                        }
                    }
                }
            });
        }
    }
    private void setCampos(){
        inputTitulo.setText(infoActividades.get(0));
        inputDescripcion.setText(infoActividades.get(1));
        inputDuracion.setText(infoActividades.get(2));


        ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) inputEncargados.getAdapter();
        int position1 = adapter1.getPosition(infoActividades.get(1));
        inputEncargados.setSelection(position1);

    }

    private boolean validarCampos(){
        String titulo = inputTitulo.getText().toString();
        String descripcion = inputDescripcion.getText().toString();
        String duracion = inputDuracion.getText().toString();
        String encargado = inputEncargados.getSelectedItem().toString();

        System.out.println(inputEncargados.getSelectedItem().toString());

        if (TextUtils.isEmpty(titulo)||  TextUtils.isEmpty(descripcion)  || TextUtils.isEmpty(duracion)  || encargado.equals("Seleccione un encargado")){
            Toast.makeText(modificar_actividad.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            return false;
        }
        return true;
    }

    private void modificarActividad(String idEvento, String idActividad) {
        if(validarCampos()){
            CollectionReference collectionRef = mfirestore.collection("Actividad");
            collectionRef.whereEqualTo("idEvento", idEvento)
                    .whereEqualTo("idActividad", idActividad)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        DocumentReference docRef = documentSnapshot.getReference();

                        docRef.update("titulo", inputTitulo.getText().toString());
                        docRef.update("descripcion", inputDescripcion.getText().toString());
                        docRef.update("duracion", inputDuracion.getText().toString());
                        docRef.update("encargado", inputEncargados.getSelectedItem().toString().substring(0, 5));
                    }
                    Toast.makeText(modificar_actividad.this, "Actividad modificada correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(modificar_actividad.this, "Error al modificar la actividad", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void limpiarCampos() {
        inputTitulo.setText("");
        inputDescripcion.setText("");
        inputDuracion.setText("");
        inputEncargados.setSelection(0);
        infoActividades.clear();
    }
}