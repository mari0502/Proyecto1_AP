package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
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
    private List<String> eventosPasados;
    private Spinner idEvento;
    private TextInputEditText comentario;
    private TextInputEditText calificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar_evento);

        db = FirebaseFirestore.getInstance();
        idEvento = findViewById(R.id.spinnereventos);
        eventosPasados = new ArrayList<String>();
        btnVolver = findViewById(R.id.btn_volver17);
        btnEnviar = findViewById(R.id.btn_Enviar);
        comentario = findViewById(R.id.comentario);
        calificacion = findViewById(R.id.calificacion);

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
                guardarEvaluacion();
            }
        });
    }

    private void getEventos(){
        /*hacer que solo se muestren eventos del dia despues de que se hicieron*/
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        System.out.println(formattedDate);

        eventosPasados.add("Seleccione un evento");
        db.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        eventosPasados.add(documento.getString("idEvento"));
                    }
                    idEvento.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventosPasados));
                }
                else {
                    Toast.makeText(Evaluar_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarEvaluacion(){
        String id = idEvento.getSelectedItem().toString();
        String textocomentario = comentario.getText().toString();
        String textocalificacion = calificacion.getText().toString();

        if (TextUtils.isEmpty(textocalificacion)|| TextUtils.isEmpty(textocomentario) || id.equals("Seleccione un evento")){
            Toast.makeText(Evaluar_Evento.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            return;
        }
        else{
            CollectionReference evaluacionesCollection = db.collection("Evaluacion");
            Random rand = new Random();
            int idEval = rand.nextInt(100);
            Evaluacion eval = new Evaluacion("Eval" + idEval, id, "", textocomentario, textocalificacion);
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
    }

    private boolean validarEncuestaUnica(){
        //se valida con el carnet
        return true;
    }

    public void limpiarCampos() {
        calificacion.setText("");
        comentario.setText("");
        idEvento.setSelection(0);
    }

}
