package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Buscar_Colaborador extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView viewInfoColaborador;
    private List<String> colaboradores;
    private Spinner spinner;
    private Button btnVolver;
    private Button btnBuscar;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_colaborador);

        db = FirebaseFirestore.getInstance();
        viewInfoColaborador = findViewById(R.id.viewInfoColaborador);
        colaboradores = new ArrayList<>();
        spinner = findViewById(R.id.spinner);
        btnVolver = findViewById(R.id.btn_volver13);
        btnBuscar = findViewById(R.id.btn_buscar4);

        getColaboradores();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(spinner.getSelectedItem().toString());
                getInfoColaborador(spinner.getSelectedItem().toString());
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Colaboradores.class);
                startActivity(intent);
            }
        });
    }

    private void getColaboradores(){
        colaboradores.add("Seleccione un colaborador");
        db.collection("usuario").whereEqualTo("idTipo", "Admin").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        colaboradores.add(documento.getString("nombre"));
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, colaboradores));
                }
                else {
                    Toast.makeText(Buscar_Colaborador.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getInfoColaborador(String nombre){
        if(nombre != "Seleccione un colaborador"){
            db.collection("usuario").whereEqualTo("nombre", nombre).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot documento : task.getResult()) {
                            String resultado = "";
                            if(Boolean.TRUE.equals(documento.getBoolean("encuesta"))){ resultado = "habilitada"; } else {resultado = "deshabilitada";};
                            String info = "Nombre: " + nombre + "\nPuesto: " + documento.getString("puesto") + "\nDescripción: " + documento.getString("descripcion") +
                                    "\nCorreo: " + documento.getString("correo")/*.substring(10)*/ + "\nContacto: " + documento.getString("contacto") + "\nCarrera: " +
                                    documento.getString("carrera") + "\nCarnet: " + documento.getString("carnet") + "\nAsociación: " + documento.getString("Asociacion");
                            viewInfoColaborador.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            viewInfoColaborador.setText(info);
                        }
                    }
                }
            });
        }
        else{
            viewInfoColaborador.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            viewInfoColaborador.setText("*Acá se muestra la información del colaborador buscado*");
            Toast.makeText(Buscar_Colaborador.this, "No se encontró ningún colaborador para buscar", Toast.LENGTH_SHORT).show();
        }
    }
}