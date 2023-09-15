package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class Inscribirse_a_evento extends AppCompatActivity {

    private TextInputEditText  nombre, correo;
    private FirebaseFirestore mfirestore;

    private Spinner InputidEvento;




    private List<String> eventos;

    private Button btnInscribirse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscribirse_aevento);

        mfirestore = FirebaseFirestore.getInstance();
        eventos = new ArrayList<>();
        InputidEvento = findViewById(R.id.spinnerInscripcion);
        nombre=findViewById(R.id.nombreEst);
        correo=findViewById(R.id.contactoEst);
        btnInscribirse = findViewById(R.id.btn_inscribirse);

        getEventos();


        Button button = (Button) findViewById(R.id.btn_volverI);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenMenuPrincipalEstudiante();
            }
        });

        btnInscribirse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idEvento = InputidEvento.getSelectedItem().toString();
                String nombreusuario = nombre.getText().toString();
                String contactousuario = correo.getText().toString();
                if (!idEvento.equals("Seleccione un Evento")) {
                    crearInscripcion(idEvento, nombreusuario, contactousuario);
                } else {
                    Toast.makeText(Inscribirse_a_evento.this, "Por favor, seleccione un evento válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getEventos() {
        eventos.add("Seleccione un Evento");
        mfirestore.collection("Evento")

                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@com.example.proyecto1_ap_heyaso.NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        String capacidadString = documento.getString("capacidad");
                        try {
                            int capacidad = Integer.parseInt(capacidadString);
                            if (capacidad > 0) { // Filtra los eventos con capacidad mayor a cero
                                eventos.add(documento.getString("idEvento"));
                            }
                        } catch (NumberFormatException e) {
                            // Manejar aquí el caso en el que la capacidad no sea un número válido
                            // Puedes omitir el evento o mostrar un mensaje de error si es necesario
                        }
                    }
                    InputidEvento.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                } else {
                    Toast.makeText(Inscribirse_a_evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    private void crearInscripcion(String idEvento,String nombreusuario, String contactousuario){
        CollectionReference eventosCollection = mfirestore.collection("Inscripcion");
        Inscripcion inscripcion = new Inscripcion(idEvento,nombreusuario,contactousuario );
        eventosCollection.add(inscripcion)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            limpiarCampos();

                            Toast.makeText(Inscribirse_a_evento.this, "Inscripcion agregado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Inscribirse_a_evento.this, "Error al agregar el evento", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                });
        //Intent intent = new Intent(this, menuPrincipalEstudiante.class);

        //falta pasar datos
        //startActivity(intent);
        OpenQR();
    }

    public void limpiarCampos() {

        InputidEvento.setSelection(0);
        nombre.setText("");
        correo.setText("");

    }
    public void reOpenMenuPrincipalEstudiante() {
        Intent intent = new Intent(this, menuPrincipalEstudiante.class);
        startActivity(intent);
    }

    public void OpenQR(){
        Intent intent = new Intent(this, Pantalla_QR.class);
        intent.putExtra("estudiante", nombre.getText().toString());
        intent.putExtra("correo", correo.getText().toString());
        //falta pasar datos
        startActivity(intent);
    }

}