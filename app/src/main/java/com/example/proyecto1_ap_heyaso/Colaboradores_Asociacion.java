package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Colaboradores_Asociacion extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText asociacion, carnet, correo, puesto;
    private Button btn_back, btn_agregarColab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colaboradores_asociacion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        asociacion = findViewById(R.id.asoPertenece);
        carnet = findViewById(R.id.carnet2);
        correo = findViewById(R.id.correo2);
        puesto = findViewById(R.id.puesto);

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

    private void validarColaborador(){
        String carnetEst = carnet.getText().toString().trim();
        String correoEst = correo.getText().toString().trim();
        String aso = asociacion.getText().toString().trim();
        String posicion = puesto.getText().toString().trim();

        if(carnetEst.isEmpty() && correoEst.isEmpty() && aso.isEmpty() && posicion.isEmpty()){
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
                        boolean correoExiste = false;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("Entre a document query");
                            String carnetUsuario = document.getString("carnet");
                            String correoUsuario = document.getString("correo");

                            if (carnetUsuario.equals(carnetEst)) {
                                carnetExiste = true;
                                if(correoUsuario.equals(correoEst)) {
                                    correoExiste = true;
                                }
                            }
                        }
                        if (carnetExiste && correoExiste) {
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

                                            if (nombreAso.equals(aso)) {
                                                asoExiste = true;
                                                break;
                                            }
                                        }
                                        if (asoExiste) {
                                            //Existe  aso y usuario insertar colab
                                            agregarColaborador(carnetEst, correoEst, posicion, aso);
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

    private void agregarColaborador(String carnet, String correo, String puesto, String asociacion){

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
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }

}