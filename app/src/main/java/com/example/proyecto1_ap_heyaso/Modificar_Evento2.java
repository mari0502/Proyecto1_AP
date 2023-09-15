package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Modificar_Evento2 extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnModificar;
    private Button btnVolver;
    private List<String> asociaciones;
    private List<String> infoEvento;
    private Spinner inputCategoria;
    private Spinner inputAsociacion;
    private TextInputEditText inputTitulo;
    private TextInputEditText inputDescripcion;
    private TextInputEditText inputLugar;
    private TextInputEditText inputFecha;
    private TextInputEditText inputDuracion;
    private TextInputEditText inputRequisitos;
    private TextInputEditText inputCapacidad;
    private CheckBox inputEncuesta;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String pasarTitulo, pasarFecha, pasarLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_evento2);
        db = FirebaseFirestore.getInstance();

        asociaciones = new ArrayList<>();
        infoEvento = new ArrayList<>();
        inputTitulo = findViewById(R.id.titulo2);
        inputDescripcion = findViewById(R.id.descripcion2);
        inputCategoria = findViewById(R.id.categoria2);
        inputAsociacion = findViewById(R.id.asociacion2);
        inputLugar = findViewById(R.id.lugar2);
        inputDuracion = findViewById(R.id.duracion2);
        inputFecha = findViewById(R.id.fecha2);
        inputRequisitos = findViewById(R.id.requisitos2);
        inputEncuesta = findViewById(R.id.encuesta2);
        inputCapacidad = findViewById(R.id.capacidad2);
        btnVolver = findViewById(R.id.btn_volver4);
        btnModificar = findViewById(R.id.btn_modificar);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        getAsociaciones();
        getInfoEvento(id);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Evento.class);
                startActivity(intent);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                modificarEvento(id);
                Intent intent = new Intent(getApplicationContext(), Pantalla_Modificacion_QR.class);
                intent.putExtra("tituloEvento", pasarTitulo);
                intent.putExtra("fechaEvento", pasarFecha);
                intent.putExtra("lugarEvento", pasarLugar);
                startActivity(intent);
            }
        });

        inputFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date here
                String monthString = "";
                String dayString = "";
                if(month + 1 <= 9){
                    monthString = "0" + Integer.toString(month + 1);
                }
                else{
                    monthString = Integer.toString(month + 1);
                }
                if(dayOfMonth <= 9){
                    dayString = "0" + Integer.toString(dayOfMonth);
                }
                else{
                    dayString = Integer.toString(dayOfMonth);
                }
                String selectedDate = dayString + "/" + monthString + "/" + Integer.toString(year).substring(2,4);
                inputFecha.setText(selectedDate);
            }
        };
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                year,
                month,
                dayOfMonth);
        datePickerDialog.show();
    }


    private void getAsociaciones(){
        asociaciones.add("Seleccione una asociación");
        db.collection("Asociacion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        asociaciones.add(documento.getString("idAsociacion"));
                    }
                    inputAsociacion.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, asociaciones));
                }
                else {
                    Toast.makeText(Modificar_Evento2.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getInfoEvento(String id){
        if(id != "Seleccione un evento"){
            db.collection("Evento").whereEqualTo("idEvento", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot documento : task.getResult()) {
                            infoEvento.add(documento.getString("titulo"));
                            infoEvento.add(documento.getString("categoria"));
                            infoEvento.add(documento.getString("descripcion"));
                            infoEvento.add(documento.getString("fecha"));
                            infoEvento.add(documento.getString("duracion"));
                            infoEvento.add(documento.getString("lugar"));
                            infoEvento.add(documento.getString("requisitos"));
                            infoEvento.add(documento.getString("idAsociacion"));
                            infoEvento.add(documento.getString("capacidad"));

                            if(documento.getBoolean("encuesta")) {
                                inputEncuesta.setChecked(true);
                            }
                            else{
                                inputEncuesta.setChecked(false);
                            }
                            setCampos();
                        }
                    }
                }
            });
        }
    }

    private void setCampos(){
        inputTitulo.setText(infoEvento.get(0));
        inputDescripcion.setText(infoEvento.get(2));
        inputFecha.setText(infoEvento.get(3));
        inputDuracion.setText(infoEvento.get(4));
        inputLugar.setText(infoEvento.get(5));
        inputRequisitos.setText(infoEvento.get(6));

        ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) inputCategoria.getAdapter();
        int position1 = adapter1.getPosition(infoEvento.get(1));
        inputCategoria.setSelection(position1);

        ArrayAdapter<String> adapter2 = (ArrayAdapter<String>) inputAsociacion.getAdapter();
        int position2 = adapter2.getPosition(infoEvento.get(7));
        inputAsociacion.setSelection(position2);

        inputCapacidad.setText(infoEvento.get(8));
    }

    private boolean validarCampos(){
        String titulo = inputTitulo.getText().toString();
        String descripcion = inputDescripcion.getText().toString();
        String categoria = inputCategoria.getSelectedItem().toString();
        String asociacion = inputAsociacion.getSelectedItem().toString();
        String lugar = inputLugar.getText().toString();
        String capacidad = inputCapacidad.getText().toString();
        String duracion = inputDuracion.getText().toString();
        String fecha = inputFecha.getText().toString();

        System.out.println(inputCategoria.getSelectedItem().toString());

        if (TextUtils.isEmpty(titulo)||  TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(lugar) || TextUtils.isEmpty(capacidad) || TextUtils.isEmpty(duracion) || TextUtils.isEmpty(fecha) || categoria.equals("Seleccione una categoría") || asociacion.equals("Seleccione una asociación")){
            Toast.makeText(Modificar_Evento2.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            return false;
        }
        return true;
    }

    private void modificarEvento(String id) {
        if(validarCampos()){
            CollectionReference collectionRef = db.collection("Evento");
            collectionRef.whereEqualTo("idEvento", id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        DocumentReference docRef = documentSnapshot.getReference();

                        docRef.update("titulo", inputTitulo.getText().toString());
                        docRef.update("descripcion", inputDescripcion.getText().toString());
                        docRef.update("duracion", inputDuracion.getText().toString());
                        docRef.update("lugar", inputLugar.getText().toString());
                        docRef.update("requisitos", inputRequisitos.getText().toString());
                        docRef.update("fecha", inputFecha.getText().toString());
                        docRef.update("capacidad", inputCapacidad.getText().toString());
                        docRef.update("idAsociacion", inputAsociacion.getSelectedItem().toString());
                        docRef.update("categoria", inputCategoria.getSelectedItem().toString());
                        docRef.update("encuesta", (inputEncuesta.isChecked()) ? true : false);
                    }
                    pasarTitulo =  inputTitulo.getText().toString();
                    pasarFecha = inputFecha.getText().toString();
                    pasarLugar = inputLugar.getText().toString();
                    Toast.makeText(Modificar_Evento2.this, "Evento modificado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(Modificar_Evento2.this, "Error al modificar el evento", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void limpiarCampos() {
        inputTitulo.setText("");
        inputDescripcion.setText("");
        inputLugar.setText("");
        inputFecha.setText("");
        inputDuracion.setText("");
        inputRequisitos.setText("");
        inputEncuesta.setChecked(false);
        inputCategoria.setSelection(0);
        inputAsociacion.setSelection(0);
        infoEvento.clear();
    }
}