package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class Buscar_Actividad extends AppCompatActivity {

    private FirebaseFirestore mfirestore;
    private TextInputEditText idEvento;
    private TextInputEditText idActividad;
    private TextInputEditText descripcion;
    private TextInputEditText duracion;
    private TextInputEditText encargado;
    private TextInputEditText capacidad;
    private TextInputEditText titulo;


    private List<String> infoActividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_actividad);

        mfirestore = FirebaseFirestore.getInstance();
        idEvento = findViewById(R.id.IdEvento);
        descripcion = findViewById(R.id.descripcion);
        duracion = findViewById(R.id.duracion);
        encargado = findViewById(R.id.encargado);
        capacidad = findViewById(R.id.capacidad);
        idActividad = findViewById(R.id.IdActividad);
        titulo = findViewById(R.id.titulo);
        infoActividad = new ArrayList<>();

        Button buscar = findViewById(R.id.btn_buscar);

        AlertDialog.Builder alerta = new AlertDialog.Builder(Buscar_Actividad.this);
        alerta.setCancelable(true);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validTextInput(idActividad)&&validTextInput(idEvento)){
                    getInfoActividad(idActividad.getText().toString(), idEvento.getText().toString());
                    Handler handler = new Handler();
                    Runnable r = new Runnable() {
                        public void run() {
                            if(infoActividad.isEmpty()){
                                //no encuentra actividad
                                alerta.setTitle("Error");
                                alerta.setMessage("No se encontró ninguna actividad");
                                alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                alerta.show();
                                resetEmpty(true);
                            }
                            else{
                                //encuentra actividad y edita campos en la pantalla
                                idActividad.setText(infoActividad.get(0));
                                idEvento.setText(infoActividad.get(1));
                                descripcion.setText(infoActividad.get(2));
                                duracion.setText(infoActividad.get(3));
                                encargado.setText(infoActividad.get(4));
                                capacidad.setText(infoActividad.get(5));
                                titulo.setText(infoActividad.get(6));


                            }
                        }
                    };
                    handler.postDelayed(r, 2000);
                    resetEmpty(false);

                }
                infoActividad.clear();
            }
        });

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reAdministrarAgenda();
            }
        });
    }

    private boolean validTextInput(TextInputEditText txtinput) {
        String result = txtinput.getText().toString();
        if(result.isEmpty()){
            txtinput.setError("Debe ingresar un número de actividad");
            return false;
        }
        else{
            txtinput.setError(null);
            return true;
        }
    }

    private void getInfoActividad(String idActividad, String idEvento){
        Query query = mfirestore.collection("Actividad");
        if (idActividad != null && !idActividad.isEmpty()&& idEvento != null && !idEvento.isEmpty()) {
                query = query.whereEqualTo("idActividad", "Act" + idActividad)
                             .whereEqualTo("idEvento", "Evento" + idEvento);
                                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            infoActividad.add(documentSnapshot.getString("idActividad"));
                                            infoActividad.add(documentSnapshot.getString("idEvento"));
                                            infoActividad.add(documentSnapshot.getString("descripcion"));
                                            infoActividad.add(documentSnapshot.getString("duracion"));
                                            infoActividad.add(documentSnapshot.getString("encargado"));
                                            infoActividad.add(documentSnapshot.getString("capacidad"));
                                            infoActividad.add(documentSnapshot.getString("titulo"));
                                        }
                                    }
                                });
        }
    }
    private void resetEmpty(boolean e){
        if(e){ idActividad.setText(""); idEvento.setText("");}
        descripcion.setText("");
        duracion.setText("");
        encargado.setText("");
        capacidad.setText("");
        titulo.setText("");

    }
    public void reAdministrarAgenda() {
        Intent intent = new Intent(this, AdministrarAgenda.class);
        startActivity(intent);
    }
}