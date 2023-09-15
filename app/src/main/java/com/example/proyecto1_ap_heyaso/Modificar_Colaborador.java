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

public class Modificar_Colaborador extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<String> colaboradores;
    private Button btnBuscar;
    private Button btnVolver;
    private Spinner inputId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_colaborador);

        colaboradores = new ArrayList<>();
        inputId = findViewById(R.id.colaborador);
        btnBuscar = findViewById(R.id.btn_buscar3);
        btnVolver = findViewById(R.id.btn_volver15);

        getColaboradores();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //que pase a modificar 2 y lleve el id del evento
                Intent intent = new Intent(Modificar_Colaborador.this, Modificar_Colaborador2.class);
                intent.putExtra("id",inputId.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menuPrincipalColaborador.class);
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
                    inputId.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, colaboradores));
                }
                else {
                    Toast.makeText(Modificar_Colaborador.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}