package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Propuesta_Evento extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String titulo, descripcion, categoria,objetivos, actividades;
    private Spinner spinnerCategoria;
    private FirebaseFirestore base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propuesta_evento);
        activarSpinner();

        Button button = (Button) findViewById(R.id.btn_VolverForo);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenForo();
            }
        });

        Button btnEnviarPropuesta = findViewById(R.id.btn_EnviarPropuesta);
        btnEnviarPropuesta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviarPropuesta(); }
        });
    }

    private void activarSpinner(){
        spinnerCategoria = findViewById(R.id.spinnerCategoriaPropuesta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Categoría, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria  = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //No hace nada
    }

    public void reOpenForo() {
        Intent intent = new Intent(this, Pantalla_Foro.class);
        startActivity(intent);
    }

    private void enviarPropuesta(){
        TextInputEditText InputTitulo = findViewById(R.id.tituloPropuesta);
        TextInputEditText InputDescripcion = findViewById(R.id.descripcionPropuesta);
        TextInputEditText InputObjetivos = findViewById(R.id.objetivosPropuesta);
        TextInputEditText InputActividades = findViewById(R.id.actividadesPropuesta);

        titulo = InputTitulo.getText().toString();
        descripcion = InputDescripcion.getText().toString();
        objetivos = InputObjetivos.getText().toString();
        actividades = InputActividades.getText().toString();

        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(objetivos) || TextUtils.isEmpty(actividades)) {
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            base = FirebaseFirestore.getInstance();
            enviarPropuestaFirestore();
        }
    }

    private void enviarPropuestaFirestore(){
        Propuesta data = new Propuesta(titulo, descripcion, objetivos, categoria, actividades);

        base.collection("Propuestas")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Propuesta_Evento.this, "Su propuesta ha sido enviada.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Propuesta_Evento.this, "Error de envío. Intente más tarde.", Toast.LENGTH_SHORT).show();
                    }
                });
        reOpenForo();
    }
}