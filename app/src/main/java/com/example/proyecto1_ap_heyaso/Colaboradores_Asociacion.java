package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Colaboradores_Asociacion extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText carnet;
    private Button btn_back, btn_agregarColab;
    private Spinner spinner, spinAsociaciones;
    private String puesto, asociacion;
    private List<String> listaAsociaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colaboradores_asociacion);

        //Obtiene el objeto
        Intent intent = getIntent();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        carnet = findViewById(R.id.carnet2);
        spinAsociaciones = findViewById(R.id.spinner5);

        activarSpinner();
        activarSpinnerAsociacion();

        btn_agregarColab = (Button) findViewById(R.id.btn_annadirColab);
        btn_agregarColab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validarColaborador();
            }
        });

        btn_back = (Button) findViewById(R.id.btn_volver11);
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolverPantallaPrincipal();
            }
        });
    }

    private void activarSpinner(){
        spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Puesto, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                puesto = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No hace nada
            }
        });
    }

    private void activarSpinnerAsociacion(){
        ;
        ArrayList<String> nombresAsociacion = new ArrayList<>();

        db.collection("Asociacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Recupera el campo "nombre" (ajusta esto según tu estructura de datos)
                                String nombre = document.getString("nombre");
                                nombresAsociacion.add(nombre);
                            }

                            // Llena el Spinner con la lista de nombres
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, nombresAsociacion);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinAsociaciones.setAdapter(adapter);
                        } else {
                            // Manejar errores aquí
                        }
                    }
                });
        spinAsociaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                asociacion = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No hace nada
            }
        });
    }

    private void validarColaborador(){
        String carnetEst = carnet.getText().toString().trim();
        //String correoEst = correo.getText().toString().trim();
        String aso = asociacion;
        String posicion = puesto;
        Toast.makeText(Colaboradores_Asociacion.this, aso, Toast.LENGTH_SHORT).show();

        if(carnetEst.isEmpty() && aso.isEmpty() && posicion.isEmpty()){
            Toast.makeText(Colaboradores_Asociacion.this, "Complete los datos solicitados para el añadir el colaborador.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            System.out.println("Seleccionar collection");
            CollectionReference usuarioRef = db.collection("usuario");
            CollectionReference asociaRef = db.collection("Asociacion");

            //Verificar si existe un usuario con mismo correo o carnet
            Query queryEst = usuarioRef.whereEqualTo("carnet", carnetEst);
            Query queryAso = asociaRef.whereEqualTo("nombre", aso);
            queryEst.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        System.out.println("Entry task is successfull");
                        boolean carnetExiste = false;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("Entre a document query");
                            String carnetUsuario = document.getString("carnet");
                            String correoUsuario = document.getString("correo");

                            if (carnetUsuario.equals(carnetEst)) {
                                carnetExiste = true;
                            }
                        }
                        if (carnetExiste) {
                            //Existe el usuario validar asociacion
                            queryAso.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        System.out.println("Entry task is successfull");
                                        boolean asoExiste = false;

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            System.out.println("Entre a document query");
                                            String nombreAso = document.getString("nombre");
                                            String idAso = document.getString("idAsociacion");

                                            if (nombreAso.equals(aso)) {
                                                asoExiste = true;
                                                break;
                                            }
                                        }
                                        if (asoExiste) {
                                            //Existe  aso y usuario insertar colab
                                            Toast.makeText(Colaboradores_Asociacion.this, "va a entrar a insertar", Toast.LENGTH_SHORT).show();
                                            agregarColaborador(carnetEst, posicion, aso);
                                        }
                                        else {
                                            Toast.makeText(Colaboradores_Asociacion.this, "La asociación no esta registrada.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Colaboradores_Asociacion.this, Colaboradores_Asociacion.class));
                                        }
                                    }
                                    else {
                                        Toast.makeText(Colaboradores_Asociacion.this, "Error al verificar la existencia de ASO", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(Colaboradores_Asociacion.this, "Carnet o correo inválido.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Colaboradores_Asociacion.this, Colaboradores_Asociacion.class));
                        }
                    }
                    else {
                        Toast.makeText(Colaboradores_Asociacion.this, "Error al verificar la existencia del usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void agregarColaborador(String carnet, String puesto, String asociacion){

        DocumentReference updateUsuario = db.collection("usuario").document(carnet);

        CollectionReference asociacionRef = db.collection("Asociacion");
        Query query = asociacionRef.whereEqualTo("nombre", asociacion);

        // Ejecuta la consulta
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Verifica si se encontraron resultados
                    if (!task.getResult().isEmpty()) {
                        // Obtén el primer documento que coincide (puede haber varios, pero aquí se toma el primero)
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);

                        // Obtiene el valor del campo "idAsociacion"
                        String idAsociacion = document.getString("idAsociacion");

                        // Haz algo con el idAsociacion
                        if (idAsociacion != null) {
                            // Aquí puedes usar idAsociacion
                            updateUsuario.update("idTipo", "Admin");
                            updateUsuario.update("puesto", puesto);
                            updateUsuario.update("idAsociacion", idAsociacion).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Colaboradores_Asociacion.this, "Tipo usuario actualizado con éxito", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Colaboradores_Asociacion.this, "Fallo actualizar tipo usuario", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent intent = new Intent(Colaboradores_Asociacion.this, Colaboradores_Asociacion.class);
                            startActivity(intent);
                        }

                    }
                }
            }
        });
    }



    //Funciones ir pantallas
    public void OpenVolverPantallaPrincipal() {
        /*Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);*/
        onBackPressed();
    }

}