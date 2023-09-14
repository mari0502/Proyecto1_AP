package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Evaluar_Evento extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnVolver;
    private Button btnEnviar;
    private List<String> eventosPasados;
    private TextView titulo;
    private TextInputEditText comentario;
    private TextInputEditText calificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar_evento);

        db = FirebaseFirestore.getInstance();
        btnVolver = findViewById(R.id.btn_volver17);
        btnEnviar = findViewById(R.id.btn_Enviar);
        titulo = findViewById(R.id.textotitulo);
        comentario = findViewById(R.id.comentario);
        calificacion = findViewById(R.id.calificacion);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pantalla_Foro.class);
                startActivity(intent);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getEventos(){
        eventos.add("Seleccione un evento");
        db.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        eventos.add(documento.getString("idEvento"));
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                }
                else {
                    Toast.makeText(Evaluar_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
