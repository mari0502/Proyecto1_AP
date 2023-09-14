package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;



import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;

import java.util.List;


public class modificar_actividad extends AppCompatActivity {
    private FirebaseFirestore mfirestore;
    private TextInputEditText idEvento;
    private TextInputEditText idActividad;
    private TextInputEditText descripcion;
    private TextInputEditText duracion;
    private Spinner encargado;
    private TextInputEditText capacidad;
    private TextInputEditText titulo;

    private List<String> infoActividad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_actividad);

        mfirestore= FirebaseFirestore.getInstance();
        idEvento = findViewById(R.id.idEvento);
        descripcion = findViewById(R.id.descripcion);
        duracion = findViewById(R.id.duracion);
        encargado = findViewById(R.id.spinnerEncargados);
        capacidad = findViewById(R.id.capacidad);
        idActividad = findViewById(R.id.idActividad);
        titulo = findViewById(R.id.titulo);
        infoActividad = new ArrayList<>();
        Button buscar = findViewById(R.id.btn_buscarA);
        Button guardar = findViewById(R.id.btn_modificarA);

        AlertDialog.Builder alerta = new AlertDialog.Builder(modificar_actividad.this);
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
                                titulo.setText(infoActividad.get(2));
                                descripcion.setText(infoActividad.get(3));
                                duracion.setText(infoActividad.get(4));
                                capacidad.setText(infoActividad.get(6));
                                editSpinnerSeleccionado(encargado);




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
                reOpenAdministrarAgenda();
            }

        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validTextInput(idActividad) && validTextInput(idEvento) && validTextInput(titulo) && validTextInput(descripcion) && validTextInput(duracion) && validTextInput(capacidad)) {
                    updateInfoActividad(idActividad.getText().toString(), idEvento.getText().toString(), alerta);
                }
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
                            infoActividad.add(documentSnapshot.getString("titulo"));
                            infoActividad.add(documentSnapshot.getString("descripcion"));
                            infoActividad.add(documentSnapshot.getString("duracion"));
                            infoActividad.add(documentSnapshot.getString("encargado"));
                            infoActividad.add(documentSnapshot.getString("capacidad"));
                        }
                    }
                });
        }
    }
    private void resetEmpty(boolean e){
        if(e){ idActividad.setText(""); idEvento.setText("");}
        titulo.setText("");
        descripcion.setText("");
        duracion.setText("");
        encargado.setSelection(0);
        capacidad.setText("");

    }
    private void editSpinnerSeleccionado(Spinner spinE){
        String strE = infoActividad.get(5).toUpperCase();

        int posFinalE = 0;

        if(strE.equalsIgnoreCase(spinE.getItemAtPosition(1).toString())){
            posFinalE = 1;
        }
        else if (strE.equalsIgnoreCase(spinE.getItemAtPosition(2).toString())){
            posFinalE = 2;
        }

        spinE.setSelection(posFinalE);
    }
    private void updateInfoActividad(String idActividad, String idEvento, AlertDialog.Builder alerta) {
        CollectionReference collectionRef = mfirestore.collection("Actividad");


        collectionRef.whereEqualTo("idActividad", "Act" + idActividad)
                     .whereEqualTo("idEvento", "Evento" + idEvento).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    DocumentReference docRef = documentSnapshot.getReference();

                    docRef.update("titulo", titulo.getText().toString());
                    docRef.update("descripcion", descripcion.getText().toString());
                    docRef.update("duracion", duracion.getText().toString());
                    docRef.update("encargado", encargado.getSelectedItem().toString().substring(0, 1).toUpperCase() + encargado.getSelectedItem().toString().substring(1).toLowerCase());
                    docRef.update("capacidad", capacidad.getText().toString());



                }

                alerta.setTitle("Éxito");
                alerta.setMessage("Act " + idActividad + " ha sido modificada");
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
                alerta.setTitle("Error");
                alerta.setMessage("Hubo un problema al modificar, intente de nuevo");
                alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alerta.show();
                resetEmpty(false);
            }
        });
        reOpenAdministrarAgenda();
    }
    public void reOpenAdministrarAgenda() {
        Intent intent = new Intent(this, AdministrarAgenda.class);
        startActivity(intent);
    }

}