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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Registrar_Asociacion extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText idAsocia, nombreAsocia, codCarrera, contacto, descripcion;
    private Button btn_agregarAso, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_asociacion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        idAsocia = findViewById(R.id.idAso);
        nombreAsocia = findViewById(R.id.nombre3);
        codCarrera = findViewById(R.id.codigoCarrera);
        contacto = findViewById(R.id.contacto3);
        descripcion = findViewById(R.id.descripcion4);

        // Boton | Accion para ir pantallas correspondientes
        btn_agregarAso = (Button) findViewById(R.id.btn_continuar);
        btn_agregarAso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { validarAso(); }
        });

        btn_back = (Button) findViewById(R.id.btn_volver12);
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolverPantallaPrincipal();
            }
        });

    }

    //Funciones ir pantallas
    private void validarAso(){

        String idAsociacion = idAsocia.getText().toString().trim();
        String nombreAso = nombreAsocia.getText().toString().trim();
        String carrera = codCarrera.getText().toString().trim();
        String info = contacto.getText().toString().trim();
        String detalle = descripcion.getText().toString().trim();

        String idAso = "Aso"+idAsociacion;

        if(idAsociacion.isEmpty() && nombreAso.isEmpty() && carrera.isEmpty() && info.isEmpty() && detalle.isEmpty()){
            Toast.makeText(Registrar_Asociacion.this, "Complete los datos solicitados para el registro.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            System.out.println("Seleccionar collection");
            CollectionReference asociaRef = db.collection("Asociacion");

            //Verificar si existe una aso con mismo id o nombre
            Query query = asociaRef.whereEqualTo("idAsociacion", idAso);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        System.out.println("Entry task is successfull");
                        boolean idExiste = false;
                        boolean asoExiste = false;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("Entre a document query");
                            String idAsoDB = document.getString("idAsociacion");

                            if (idAsoDB.equals(idAso)) {
                                idExiste = true;
                                break;
                            }
                            String nombreDB = document.getString("nombre");
                            if (nombreDB.equals(nombreAso)) {
                                asoExiste = true;
                                break;
                            }
                        }
                        if (idExiste || asoExiste) {
                            Toast.makeText(Registrar_Asociacion.this, "Ya existe una Asociación con ese id o nombre.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registrar_Asociacion.this, Registrar_Asociacion.class));
                        }
                        else {
                            //No existe un usuario agregado con ese correo o carnet
                            registrarAsociacion(idAso, nombreAso, carrera, info, detalle);
                        }
                    }
                    else {
                        Toast.makeText(Registrar_Asociacion.this, "Error al verificar la existencia de la Aso", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void registrarAsociacion(String idAso, String nombreAso, String carrera, String info, String detalle) {
        mAuth.createUserWithEmailAndPassword(idAso, nombreAso).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //String id = mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                //map.put("id", id);
                map.put("idAsociacion", idAso);
                map.put("nombre", nombreAso);
                map.put("codCarrera", carrera);
                map.put("contacto", info);
                map.put("descripcion", detalle);

                db.collection("Asociacion").document(nombreAso).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(Registrar_Asociacion.this, Colaboradores_Asociacion.class));
                        Toast.makeText(Registrar_Asociacion.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registrar_Asociacion.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OpenVolverPantallaPrincipal() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }
}