package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Eliminar_Colaborador extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnEliminar;
    private Button btnVolver;
    private Spinner spinner;
    private List<String> colaboradores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_colaborador);

        db = FirebaseFirestore.getInstance();
        colaboradores = new ArrayList<>();
        btnEliminar = findViewById(R.id.btn_eliminar3);
        btnVolver = findViewById(R.id.btn_volver14);
        spinner = findViewById(R.id.colaborador2);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarColaborador();
                Intent intent = new Intent(getApplicationContext(), Administrar_Colaboradores.class);
                startActivity(intent);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Colaboradores.class);
                startActivity(intent);
            }
        });
    }

    private void getColaboradores(){
        colaboradores.add("Seleccione un colaborador");
        db.collection("usuario").whereEqualTo("idTipo", "Admin").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        colaboradores.add(documento.getString("carnet"));
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, colaboradores));
                }
                else {
                    Toast.makeText(Eliminar_Colaborador.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eliminarColaborador(){
        //eliminar en cascada
        Toast.makeText(Eliminar_Colaborador.this, "Colaborador eliminado correctamente", Toast.LENGTH_SHORT).show();
    }
}