package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class modificar_actividad1 extends AppCompatActivity {
    private FirebaseFirestore mfirestore;

    private Spinner idEvento;
    private Spinner idActividad;

    private List<String> actividades;

    private List<String> eventos;
    private Button btnBuscar;

    private Button btnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_actividad1);
        mfirestore = FirebaseFirestore.getInstance();
        actividades = new ArrayList<>();
        eventos = new ArrayList<>();
        idEvento = findViewById(R.id.spinnerEventosA);
        idActividad = findViewById(R.id.spinnerMActividades);
        btnBuscar = findViewById(R.id.btn_buscarMA);
        btnVolver = findViewById(R.id.btn_volverMA);

        getEventos();

        idEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Cuando se selecciona un evento, cargar las actividades relacionadas
                if (position > 0) {
                    String selectedEvento = eventos.get(position);
                    getActividades(selectedEvento);
                } else {
                    // Si se selecciona "Seleccione un Evento", limpia el spinner de actividades
                    actividades.clear();
                    actividades.add("Seleccione una Actividad");
                    idActividad.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, actividades));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No es necesario hacer nada aquí
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //que pase a modificar y lleve el id del evento y de actividad
                Intent intent = new Intent(modificar_actividad1.this, modificar_actividad.class);
                intent.putExtra("idEvento", idEvento.getSelectedItem().toString());
                intent.putExtra("idActividad", idActividad.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdministrarAgenda.class);
                startActivity(intent);
            }
        });
    }

    private void getEventos() {
        eventos.add("Seleccione un Evento");
        //actividades.add("Seleccione una Actividad");
        mfirestore.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@com.example.proyecto1_ap_heyaso.NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        eventos.add(documento.getString("idEvento"));
                    }
                    idEvento.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                } else {
                    Toast.makeText(modificar_actividad1.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void getActividades(String eventoSeleccionado) {
        actividades.clear();
        actividades.add("Seleccione una Actividad");
        mfirestore.collection("Actividad")
                .whereEqualTo("idEvento", eventoSeleccionado)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@com.example.proyecto1_ap_heyaso.NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        actividades.add(documento.getString("idActividad"));
                    }
                    idActividad.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, actividades));
                } else {
                    Toast.makeText(modificar_actividad1.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


}