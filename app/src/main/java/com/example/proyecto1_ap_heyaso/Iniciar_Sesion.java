package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Iniciar_Sesion extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText correoEst, claveEst;
    private Button btn_login, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        correoEst = findViewById(R.id.correo);
        claveEst = findViewById(R.id.contrasenna2);

        // Boton | Accion para ir pantallas correspondientes

        btn_login = (Button) findViewById(R.id.btn_ingresar);
        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               validarUsuario();
            }
        });

        btn_back = (Button) findViewById(R.id.btn_volver9);
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolver();
            }
        });
    }

    //Funciones

    private void validarUsuario(){
        String correoUsuario = correoEst.getText().toString().trim();
        String claveUsuario = claveEst.getText().toString().trim();

        if(correoUsuario.isEmpty() && claveUsuario.isEmpty()){
            Toast.makeText(Iniciar_Sesion.this, "Ingrese los datos solicitados.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            System.out.println("Seleccionar collection");
            CollectionReference cuentaRef = db.collection("usuario");

            //Verificar si existe un usuario con mismo correo y contrasenna
            Query query = cuentaRef.whereEqualTo("correo", correoUsuario);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        System.out.println("Entry task is successfull");
                        boolean correoExiste = false;
                        boolean claveExiste = false;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("Entre a document query");
                            String correoCuenta = document.getString("correo");
                            String claveCuenta = document.getString("contraseña");
                            String tipoCuenta = document.getString("idTipo");

                            //Obtener otra info
                            String carnet = document.getString("carnet");
                            String nombre = document.getString("nombre");

                            //Crear el objeto usuario
                            Usuarios usuario = new Usuarios(carnet, nombre,"Sin", tipoCuenta, correoCuenta, claveCuenta);

                            if (correoCuenta.equals(correoUsuario)) {
                                //correoExiste = true;
                                if(claveCuenta.equals(claveUsuario)) {
                                    //claveExiste = true;
                                    if (tipoCuenta.equals("Estudiante")) {
                                        Intent intent = new Intent(Iniciar_Sesion.this, menuPrincipalEstudiante.class);
                                        intent.putExtra("usuarioActual", usuario);
                                        startActivity(intent);
                                        Toast.makeText(Iniciar_Sesion.this, "Bienvenido Menu Estudiante.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(Iniciar_Sesion.this, menuPrincipalColaborador.class);
                                        intent.putExtra("usuarioActual", usuario);
                                        startActivity(intent);
                                        Toast.makeText(Iniciar_Sesion.this, "Bienvenido Menu Colaborador.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                        Toast.makeText(Iniciar_Sesion.this, "Error inicio sesión.", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        }
                    }
                }
            });
        }
    }
    public void OpenVolver() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }
}