package com.example.proyecto1_ap_heyaso;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Procesar_propuestas extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView viewInfoPropuesta;
    private List<String> propuestasTitulo = new ArrayList<>();;
    private List<Propuesta> propuestasRecibidas =new ArrayList<>();;
    private List<String> idPropuestaLista = new ArrayList<>();
    private Propuesta propuesta;
    private Spinner spinnerPropuesta;
    private Button btnVolver;
    private Button btnAceptar;
    private Button btnRechazar;
    private String itemSelec, idActual ,idPropuesta, titulo, descripcion, categoria,objetivos, actividades, estadoPropuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesar_propuestas);

        db = FirebaseFirestore.getInstance();
        viewInfoPropuesta = findViewById(R.id.txtMostrarInfoPropuesta);
        spinnerPropuesta = findViewById(R.id.spinnerPropuestas);
        btnVolver = findViewById(R.id.btnVolverForoProcesar);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRechazar = findViewById(R.id.btnRechazar);
        recuperarPropuestas();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pantalla_Foro.class);
                startActivity(intent);
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoPropuesta = "Aceptada";
                agregarEstado();
            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoPropuesta = "Rechazada";
                agregarEstado();

            }
        });


    }

    private void agregarEstado(){
        if(itemSelec != "Seleccione Propuesta"){
            //Crear un mapa para los datos que quieres agregar
            Map<String, Object> datos = new HashMap<>();
            datos.put("estado", estadoPropuesta);

            //Obtener una referencia al documento que quieres actualizar
            DocumentReference docRef = db.collection("Propuestas").document(idActual);

            //Llamar al método update para agregar los nuevos datos
            docRef.update(datos)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Procesar_propuestas.this, "Se ha procesado la propuesta", Toast.LENGTH_SHORT).show();

                            //Eliminar la propuesta procesada de la lista del objeto
                            Iterator<Propuesta> iterator = propuestasRecibidas.iterator();
                            while (iterator.hasNext()) {
                                Propuesta propuesta = iterator.next();
                                if (propuesta.getIdPropuesta().equals(idActual)) {
                                    iterator.remove();
                                }
                            }
                            propuestasTitulo.remove(itemSelec);
                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerPropuesta.getAdapter();
                            adapter.notifyDataSetChanged();
                            spinnerPropuesta.setSelection(0);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error actualizando el documento", e);
                        }
                    });
        }else{
            Toast.makeText(Procesar_propuestas.this, "Debe seleccionar una propuesta.", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperarPropuestas(){
        propuestasTitulo.add("Seleccione Propuesta");
        db.collection("Propuestas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                llenarSpinner(document.getId().toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        spinnerPropuesta.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, propuestasTitulo));
        spinnerPropuesta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener el elemento seleccionado
                itemSelec = adapterView.getItemAtPosition(i).toString();
                System.out.println(itemSelec);

                if(itemSelec == "Seleccione Propuesta"){
                    viewInfoPropuesta.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    viewInfoPropuesta.setText("*Acá se muestra la información de la Propuesta*");
                }else{
                    for (Propuesta p: propuestasRecibidas) {
                        if(itemSelec == p.getTitulo()){
                            for (String id: idPropuestaLista) {
                                if(id == p.getIdPropuesta()){
                                    idActual = p.getIdPropuesta();
                                    String info = "Título: "+ p.getTitulo()+ "\nDescripción: "+ p.getDescripcion()
                                            +"\nObjetivos: "+ p.getObjetivos()+"\nCategoría: "+ p.getCategoria()
                                            +"\nActividades: "+ p.getActividades();
                                    viewInfoPropuesta.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                    viewInfoPropuesta.setText(info);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No hace nada
            }
        });
    }

    private void llenarSpinner(String id){
        db.collection("Propuestas").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    //Toma los valores del documento
                    idPropuesta = documentSnapshot.getId().toString();
                    titulo = documentSnapshot.getString("titulo");
                    descripcion = documentSnapshot.getString("descripcion");
                    categoria = documentSnapshot.getString("categoria");
                    objetivos = documentSnapshot.getString("objetivos");
                    actividades = documentSnapshot.getString("actividades");
                    estadoPropuesta = documentSnapshot.getString("estado");

                    if(estadoPropuesta.equals("SC")){
                        System.out.println("ENTROO");
                        idPropuestaLista.add(idPropuesta); //Inserta en una lista los id
                        propuestasTitulo.add(titulo); //Inserta en una lista los titulos para llenar el spinner

                        propuesta = new Propuesta(titulo, descripcion, objetivos, categoria, actividades, estadoPropuesta);
                        propuesta.setIdPropuesta(idPropuesta);
                        propuestasRecibidas.add(propuesta); //Inserte el objeto Propuesta
                    }
                }
            }
        });
    }
}