package com.example.proyecto1_ap_heyaso;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Evaluar_Evento extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnVolver;
    private Button btnEnviar;
    private List<String> eventos;
    private Spinner idEvento;
    private TextInputEditText comentario;
    private Spinner spinCalificar;
    private String calificacion;
    SimpleDateFormat fechaHoy;
    private Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar_evento);

        db = FirebaseFirestore.getInstance();
        idEvento = findViewById(R.id.spinnereventos);
        eventos = new ArrayList<String>();
        btnVolver = findViewById(R.id.btn_volver17);
        btnEnviar = findViewById(R.id.btn_Enviar);
        comentario = findViewById(R.id.comentario);

        Intent intent = getIntent();
        usuario = (Usuarios) intent.getSerializableExtra("usuarioActual");

        activarSpinner();
        getEventos();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pantalla_Foro.class);
                startActivity(intent);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });
    }

    private void activarSpinner(){
        spinCalificar = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Calificacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCalificar.setAdapter(adapter);
        spinCalificar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calificacion = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No hace nada
            }
        });
    }

    private void getEventos(){
        Date fecha = new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

        eventos.add("Seleccione un evento");
        CollectionReference eventoRef = db.collection("Evento");
        Query query = eventoRef.whereEqualTo("encuesta", true).whereEqualTo("fecha", formatter.format(fecha));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        eventos.add(documento.getString("idEvento"));
                    }
                    idEvento.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                }
                else {
                    Toast.makeText(Evaluar_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validarCampos(){
        String id = idEvento.getSelectedItem().toString();
        String textocomentario = comentario.getText().toString();
        String textocalificacion = calificacion;

        if (TextUtils.isEmpty(textocalificacion)|| TextUtils.isEmpty(textocomentario) || id.equals("Seleccione un evento")){
            Toast.makeText(Evaluar_Evento.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            return;
        }
        else{
            guardarEvaluacion(id, textocomentario, textocalificacion);
        }
    }

    private void guardarEvaluacion(String id, String textocomentario, String textocalificacion){
        CollectionReference evaluacionesCollection = db.collection("Evaluacion");
        Query query = evaluacionesCollection.whereEqualTo("idEvaluacion", id);

        Random rand = new Random();
        int idEval = rand.nextInt(100);

        //validar que no haya otro igual antes de agregar
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String idEvaluacionExiste = document.getString("idEvaluacion");

                    if (idEvaluacionExiste.equals(idEval)){
                        guardarEvaluacion(id, textocomentario, textocalificacion);
                    }
                    else{
                        break;
                    }
                }
            }
        });
        guardar(idEval, id, textocomentario, textocalificacion);
    }

    private void guardar(int idEval, String id, String textocomentario, String textocalificacion){
        CollectionReference evaluacionesCollection = db.collection("Evaluacion");
        Evaluacion eval = new Evaluacion("Eval" + idEval, id, usuario.getCarnetUsuario(), textocomentario, textocalificacion);
        evaluacionesCollection.add(eval).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    limpiarCampos();
                    Toast.makeText(Evaluar_Evento.this, "Evaluación agregada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Evaluar_Evento.this, "Error al agregar el evento", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
    }

    public void limpiarCampos() {
        spinCalificar.setSelection(0);
        comentario.setText("");
        idEvento.setSelection(0);
    }

}
