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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Gestion_CuentaEstudiante extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextInputEditText carnet, clave;
    private Button btn_close, btn_modificarUser, btn_eliminarUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cuenta_estudiante);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        carnet = findViewById(R.id.carnet3);
        clave = findViewById(R.id.contrasenna4);

        btn_modificarUser = (Button) findViewById(R.id.btn_editarCuenta);
        btn_eliminarUser = (Button) findViewById(R.id.btn_eliminarUsuario);
        btn_close = (Button) findViewById(R.id.btn_salir4);

        btn_eliminarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEliminaUsuario();
            }
        });
        //Regresar a la pantalla principal
        btn_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { openVolverMenuEst(); }
        });
    }

    private void openEliminaUsuario() {
        String carnetEst = carnet.getText().toString().trim();
        String claveEst = clave.getText().toString().trim();

        if(carnetEst.isEmpty() && claveEst.isEmpty()){
            Toast.makeText(Gestion_CuentaEstudiante.this, "Complete los datos solicitados para válidar.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            System.out.println("Seleccionar collection");
            CollectionReference cuentaRef = db.collection("usuario");

            //Verificar si existe un usuario con mismo correo y contrasenna
            Query query = cuentaRef.whereEqualTo("carnet", carnetEst);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        System.out.println("Entry task is successfull");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("Entre a document query");
                            String carnetDB = document.getString("carnet");
                            String claveDB = document.getString("contraseña");

                            if(carnetDB.equals(carnetEst) && claveDB.equals(claveEst)){
                                db.collection("usuario").document(carnetEst).delete();
                                Toast.makeText(Gestion_CuentaEstudiante.this, "Cuenta eliminada exitosamente.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Gestion_CuentaEstudiante.this, Pagina_Principal.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Gestion_CuentaEstudiante.this, "Carnet o contraseña invalido.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Gestion_CuentaEstudiante.this, Gestion_CuentaEstudiante.class);
                            }

                        }
                    } else {
                        Toast.makeText(Gestion_CuentaEstudiante.this, "Carnet o contraseña invalido.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Gestion_CuentaEstudiante.this, Gestion_CuentaEstudiante.class);
                    }
                }
            });
        }
    }

    public void openVolverMenuEst() {
        Intent intent = new Intent(Gestion_CuentaEstudiante.this, Pagina_Principal.class);
        startActivity(intent);
    }
}