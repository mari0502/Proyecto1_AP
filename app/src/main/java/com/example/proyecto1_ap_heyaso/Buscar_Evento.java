package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BuscarEvento extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private TextView eventoInfo;
    private List<String> documentos;
    private List<String> eventos;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_evento);

        eventoInfo = findViewById(R.id.eventoinfo);
        documentos = new ArrayList<>();
        eventos = new ArrayList<>();
        spinner = findViewById(R.id.spinner2) ;
        spinner.setSelection(0);
        System.out.println("todo bien");
        //getEventos();
    }


}