package com.example.proyecto1_ap_heyaso;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Procesar_propuestas extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<String> propuestasRecibidas = new ArrayList<String>();
    private String titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesar_propuestas);
        db = FirebaseFirestore.getInstance();

        recuperarPropuestas();

        Spinner spinnerTitulo = findViewById(R.id.spinnerPropuestas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Procesar_propuestas.this,
                android.R.layout.simple_spinner_dropdown_item, propuestasRecibidas);
        spinnerTitulo.setAdapter(adapter);

    }

    public void recuperarPropuestas(){
        db.collection("Propuestas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                propuestasRecibidas.add(document.getId().toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                })
    }
}