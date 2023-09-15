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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Crear_Evento extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<String> asociaciones;
    private Spinner inputCategoria;
    private Spinner inputAsociacion;
    private TextInputEditText inputId;
    private TextInputEditText inputTitulo;
    private TextInputEditText inputDescripcion;
    private TextInputEditText inputLugar;
    private TextInputEditText inputDuracion;
    private TextInputEditText inputFecha;
    private TextInputEditText inputRequisitos;
    private CheckBox inputEncuesta;
    private TextInputEditText inputCapacidad;
    private Button btnCrear;
    private Button btnVolver;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        db = FirebaseFirestore.getInstance();

        asociaciones = new ArrayList<>();
        inputId = findViewById(R.id.IdEvento);
        inputTitulo = findViewById(R.id.titulo);
        inputDescripcion = findViewById(R.id.descripcion);
        inputCategoria = findViewById(R.id.categoria);
        inputAsociacion = findViewById(R.id.asociacion);
        inputLugar = findViewById(R.id.lugar);
        inputDuracion = findViewById(R.id.duracion);
        inputFecha = findViewById(R.id.fecha);
        inputRequisitos = findViewById(R.id.requisitos);
        inputEncuesta = findViewById(R.id.encuesta);
        inputCapacidad = findViewById(R.id.capacidad3);
        btnVolver = findViewById(R.id.btn_volver20);
        btnCrear = findViewById(R.id.btn_crear);

        getAsociaciones();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Evento.class);
                startActivity(intent);
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validarEvento();
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
                    Toast.makeText(Crear_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validarEvento() {
        String idEvento = inputId.getText().toString();
        String titulo = inputTitulo.getText().toString();
        String descripcion = inputDescripcion.getText().toString();
        String categoria = inputCategoria.getSelectedItem().toString();
        String asociacion = inputAsociacion.getSelectedItem().toString();
        String capacidad = inputCapacidad.getText().toString();
        String lugar = inputLugar.getText().toString();
        String duracion = inputDuracion.getText().toString();
        String fecha = inputFecha.getText().toString();
        String requisitos = inputRequisitos.getText().toString();

        String NewidEvento = "Evento" + idEvento;

        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(idEvento) ||  TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(capacidad) ||
                TextUtils.isEmpty(lugar) || TextUtils.isEmpty(duracion) || TextUtils.isEmpty(fecha) || categoria.equals("Seleccione una categoría") ||
                asociacion.equals("Seleccione una asociación")){
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            System.out.println("Voy a seleccionar la coleccion");
            CollectionReference eventoRef = db.collection("Evento");

            // Verificar si ya existe un evento con el mismo nombre o número
            Query query = eventoRef.whereEqualTo("titulo", titulo);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        System.out.println("Entre a task is successfull");
                        boolean nombreEventoExistente = false;
                        boolean numeroEventoExistente = false;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("Entre a document query");
                            String idEventoExistente = document.getString("IdEvento");

                            if (idEventoExistente.equals(NewidEvento)) {
                                numeroEventoExistente = true;
                                break;
                            }
                            nombreEventoExistente = true;
                        }

                        if (nombreEventoExistente) {
                            Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo nombre", Toast.LENGTH_SHORT).show();
                        }
                        else if (numeroEventoExistente) {
                            Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo número", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            eventoRef.whereEqualTo("idEvento", NewidEvento).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        boolean idEventoExist = false;

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String idEventoExistente = document.getString("idEvento");
                                            if (idEventoExistente.equals(NewidEvento)) {
                                                idEventoExist = true;
                                                break;
                                            }
                                        }

                                        if (idEventoExist) {
                                            Toast.makeText(Crear_Evento.this, "Ya existe un evento con el mismo numero", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            // No existe un evento con el mismo nombre o número, agregar a Firestore

                                            // revisa checkbox encuesta
                                            Boolean encuesta = false;
                                            if (inputEncuesta.isChecked()) { encuesta = true; }
                                            crearEvento("Evento" + idEvento, titulo, descripcion, categoria, asociacion, lugar, duracion, fecha, requisitos, encuesta, capacidad);
                                        }
                                    }
                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(Crear_Evento.this, "Error al verificar la existencia del evento", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void crearEvento(String idEvento, String titulo, String descripcion, String categoria, String asociacion, String lugar, String duracion, String fecha, String requisitos, Boolean encuesta, String capacidad){
        CollectionReference eventosCollection = db.collection("Evento");
        Evento evento = new Evento(idEvento, titulo, descripcion, categoria, asociacion, lugar, duracion, fecha, requisitos, encuesta, capacidad);
        eventosCollection.add(evento)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            limpiarCampos();
                            Toast.makeText(Crear_Evento.this, "Evento agregado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Crear_Evento.this, "Error al agregar el evento", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                });
    }

    public void limpiarCampos() {
        inputId.setText("");
        inputTitulo.setText("");
        inputDescripcion.setText("");
        inputLugar.setText("");
        inputFecha.setText("");
        inputDuracion.setText("");
        inputRequisitos.setText("");
        inputCapacidad.setText("");
        inputEncuesta.setChecked(false);
        inputCategoria.setSelection(0);
        inputAsociacion.setSelection(0);
    }
}