package com.example.proyecto1_ap_heyaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Modificar_Colaborador2 extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnModificar;
    private Button btnVolver;
    private List<String> asociaciones;
    private List<String> infoColaborador;
    private Spinner inputAsociacion;
    private TextInputEditText nombre;
    private TextInputEditText carrera;
    private TextInputEditText puesto;
    private TextInputEditText correo;
    private TextInputEditText contrasenna;
    private TextInputEditText contacto;
    private TextInputEditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_colaborador2);

        db = FirebaseFirestore.getInstance();
        asociaciones = new ArrayList<>();
        infoColaborador = new ArrayList<>();
        inputAsociacion = findViewById(R.id.asociacion3);
        nombre = findViewById(R.id.nombre);
        carrera = findViewById(R.id.carrera2);
        puesto = findViewById(R.id.puesto2);
        correo = findViewById(R.id.correo4);
        nombre = findViewById(R.id.nombre);
        contrasenna = findViewById(R.id.contrasenna3);
        contacto = findViewById(R.id.contacto4);
        descripcion = findViewById(R.id.descripcion5);
        btnModificar = findViewById(R.id.btn_modificar9);
        btnVolver = findViewById(R.id.btn_volver10);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        getAsociaciones();
        getInfoColaborador(id);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menuPrincipalColaborador.class);
                startActivity(intent);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarColaborador(id);
                Intent intent = new Intent(getApplicationContext(), Modificar_Colaborador.class);
                startActivity(intent);
            }
        });
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
                    Toast.makeText(Modificar_Colaborador2.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void modificarColaborador(String id) {
        if(validarCampos()){
            CollectionReference collectionRef = db.collection("usuario");
            collectionRef.whereEqualTo("carnet", id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        DocumentReference docRef = documentSnapshot.getReference();

                        docRef.update("nombre", nombre.getText().toString());
                        docRef.update("descripcion", descripcion.getText().toString());
                        docRef.update("carrera", carrera.getText().toString());
                        docRef.update("puesto", puesto.getText().toString());
                        docRef.update("correo", correo.getText().toString());
                        docRef.update("contraseña", contrasenna.getText().toString());
                        docRef.update("idAsociacion", inputAsociacion.getSelectedItem().toString().substring(0, 5));
                        docRef.update("contacto", contacto.getText().toString());
                    }
                    Toast.makeText(Modificar_Colaborador2.this, "Colaborador modificado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(Modificar_Colaborador2.this, "Error al modificar el colaborador", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getInfoColaborador(String id){
        if(id != "Seleccione un colaborador"){
            db.collection("usuario").whereEqualTo("nombre", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot documento : task.getResult()) {
                            infoColaborador.add(documento.getString("nombre"));
                            infoColaborador.add(documento.getString("carrera"));
                            infoColaborador.add(documento.getString("idAsociacion"));
                            infoColaborador.add(documento.getString("puesto"));
                            infoColaborador.add(documento.getString("correo"));
                            infoColaborador.add(documento.getString("contraseña"));
                            infoColaborador.add(documento.getString("contacto"));
                            infoColaborador.add(documento.getString("descripcion"));
                            setCampos();
                        }
                    }
                }
            });
        }
    }

    private void setCampos(){
        nombre.setText(infoColaborador.get(0));
        carrera.setText(infoColaborador.get(1));
        puesto.setText(infoColaborador.get(3));
        correo.setText(infoColaborador.get(4));
        contrasenna.setText(infoColaborador.get(5));
        contacto.setText(infoColaborador.get(6));
        descripcion.setText(infoColaborador.get(7));


        ArrayAdapter<String> adapter2 = (ArrayAdapter<String>) inputAsociacion.getAdapter();
        int position2 = adapter2.getPosition(infoColaborador.get(2));
        inputAsociacion.setSelection(position2);
    }

    private boolean validarCampos(){
        String vnombre = nombre.getText().toString();
        String vcarrera = carrera.getText().toString();
        String vasociacion = inputAsociacion.getSelectedItem().toString();
        String vpuesto = puesto.getText().toString();
        String vcorreo = correo.getText().toString();
        String vcontrasenna = contrasenna.getText().toString();
        String vcontacto = contacto.getText().toString();
        String vdescripcion = descripcion.getText().toString();


        if (TextUtils.isEmpty(vnombre)||  TextUtils.isEmpty(vcarrera) || TextUtils.isEmpty(vpuesto) || TextUtils.isEmpty(vcorreo) || TextUtils.isEmpty(vcontrasenna) || TextUtils.isEmpty(vcontacto) || TextUtils.isEmpty(vdescripcion) || vasociacion.equals("Seleccione una asociación")){
            Toast.makeText(Modificar_Colaborador2.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            return false;
        }
        return true;
    }

    public void limpiarCampos() {
        nombre.setText("");
        carrera.setText("");
        puesto.setText("");
        correo.setText("");
        contrasenna.setText("");
        contacto.setText("");
        descripcion.setText("");
        inputAsociacion.setSelection(0);
        infoColaborador.clear();
    }

}