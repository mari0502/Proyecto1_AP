package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registrar_Estudiante extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText nombre, carrera, carnet, correo, clave, contacto, descripcion;
    private Button btn_back, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estudiante);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.nombre2);
        carrera = findViewById(R.id.carrera);
        carnet = findViewById(R.id.carnet);
        correo = findViewById(R.id.correo3);
        clave = findViewById(R.id.contrasenna);
        contacto = findViewById(R.id.contacto2);
        descripcion = findViewById(R.id.descripcion3);
        btn_back = findViewById(R.id.btn_volver8);
        btn_register = findViewById(R.id.btn_crearCuenta);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });
        //Regresar a la pantalla principal
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolverPantallaPrincipal();
            }
        });
    }

    //Funciones ir pantallas
    public void OpenVolverPantallaPrincipal() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }

    private void validarUsuario(){
        String nombreEst = nombre.getText().toString().trim();
        String carreraEst = carrera.getText().toString().trim();
        String carnetEst = carnet.getText().toString().trim();
        String correoEst = correo.getText().toString().trim();
        String claveEst = clave.getText().toString().trim();
        String contactoEst = contacto.getText().toString().trim();
        String descripcionEst = descripcion.getText().toString().trim();

        if(nombreEst.isEmpty() && carreraEst.isEmpty() && carnetEst.isEmpty() && correoEst.isEmpty() && claveEst.isEmpty() &&
                contactoEst.isEmpty() && descripcionEst.isEmpty()){
            Toast.makeText(Registrar_Estudiante.this, "Complete los datos solicitados para el registro.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //Verificar si es correo institucional
            if(correoEst.contains("@estudiantec.cr")){
                System.out.println("Seleccionar collection");
                CollectionReference usuarioRef = db.collection("usuario");

                //Verificar si existe un usuario con mismo correo o carnet
                Query query = usuarioRef.whereEqualTo("carnet", carnetEst);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            System.out.println("Entry task is successfull");
                            boolean carnetExiste = false;
                            boolean correoExiste = false;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("Entre a document query");
                                String carnetUsuario = document.getString("carnet");

                                if (carnetUsuario.equals(carnetEst)) {
                                    carnetExiste = true;
                                    break;
                                }
                                String correoDB = document.getString("correo");
                                if (correoDB.equals(correoEst)) {
                                    correoExiste = true;
                                    break;
                                }
                            }
                            if (carnetExiste || correoExiste) {
                                Toast.makeText(Registrar_Estudiante.this, "Ya existe un usuario con ese correo o carnet.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registrar_Estudiante.this, Registrar_Estudiante.class));
                            }
                            else {
                                //No existe un usuario agregado con ese correo o carnet
                                registrarEstudiante(nombreEst, carreraEst, carnetEst, correoEst, claveEst, contactoEst, descripcionEst);
                            }
                        }
                        else {
                            Toast.makeText(Registrar_Estudiante.this, "Error al verificar la existencia del usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(Registrar_Estudiante.this, "Correo debe ser institucional", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void registrarEstudiante(String nombre, String carrera, String carnet, String correo, String contrasenna, String contacto, String descripcion){
        mAuth.createUserWithEmailAndPassword(carnet, correo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //String id = mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                //map.put("id", id);
                map.put("carnet", carnet);
                map.put("nombre", nombre);
                map.put("correo", correo);
                map.put("carrera", carrera);
                map.put("contacto", contacto);
                map.put("contraseña", contrasenna);
                map.put("descripcion", descripcion);
                map.put("idTipo", "Estudiante");
                map.put("idAsociacion", " ");
                map.put("puesto", " ");
                map.put("idAsociacion", "");
                map.put("puesto", "");

                db.collection("usuario").document(carnet).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(Registrar_Estudiante.this, Iniciar_Sesion.class));
                        Toast.makeText(Registrar_Estudiante.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registrar_Estudiante.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}