package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class Modificar_Asociacion extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText idAso, nombreAsocia, codCarrera, contacto, descripcion;
    private Button btn_actualizarAso, btn_back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_asociacion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        idAso = findViewById(R.id.idAso2);
        nombreAsocia = findViewById(R.id.nombre5);
        codCarrera = findViewById(R.id.codigoCarrera2);
        contacto = findViewById(R.id.contacto6);
        descripcion = findViewById(R.id.descripcion7);
        btn_actualizarAso = findViewById(R.id.btn_actualizar);
        btn_back = findViewById(R.id.btn_volver19);
        btn_actualizarAso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { validarAso(); }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolverPantallaPrincipal();
            }
        });

    }

    //Funciones ir pantallas
    private void validarAso(){

        String identificador = idAso.getText().toString().trim();
        String nombreAso = nombreAsocia.getText().toString().trim();
        String carrera = codCarrera.getText().toString().trim();
        String info = contacto.getText().toString().trim();
        String detalle = descripcion.getText().toString().trim();

        //String idAso = "Aso"+idAsociacion;

        if(identificador.isEmpty() && nombreAso.isEmpty() && carrera.isEmpty() && info.isEmpty() && detalle.isEmpty()){
            Toast.makeText(Modificar_Asociacion.this, "Complete los datos solicitados para el registro.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //Verificar si es correo institucional

                System.out.println("Seleccionar collection");
                CollectionReference asociaRef = db.collection("Asociacion");

                //Verificar si existe un usuario con mismo correo o carnet
                Query query = asociaRef.whereEqualTo("idAsociacion", identificador);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            System.out.println("Entry task is successfull");
                            boolean asoExiste = false;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("Entre a document query");
                                String idAsoDB = document.getString("idAsociacion");

                                if (idAsoDB.equals(identificador)) {
                                    asoExiste = true;
                                    break;
                                }
                            }
                            if (asoExiste) {
                                registrarAsociacion(identificador, nombreAso, carrera, info, detalle);
                            }
                            else {
                                //No existe un usuario agregado con ese correo o carnet
                                Toast.makeText(Modificar_Asociacion.this, "Asociacion no existe.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Modificar_Asociacion.this, Modificar_Asociacion.class));
                            }
                        }
                        else {
                            Toast.makeText(Modificar_Asociacion.this, "Error al verificar la existencia del usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    }

    public void registrarAsociacion(String identificador, String nombreAso, String carrera, String info, String detalle) {
        mAuth.createUserWithEmailAndPassword(identificador, nombreAso).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {
                //String id = mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                //map.put("id", id);
                //map.put("idAsociacion", idAso);
                map.put("nombre", nombreAso);
                map.put("codCarrera", carrera);
                map.put("contacto", info);
                map.put("descripcion", detalle);

                db.collection("Asociacion").document(identificador).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(Modificar_Asociacion.this, Modificar_Asociacion.class));
                        Toast.makeText(Modificar_Asociacion.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Modificar_Asociacion.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OpenVolverPantallaPrincipal() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }
}