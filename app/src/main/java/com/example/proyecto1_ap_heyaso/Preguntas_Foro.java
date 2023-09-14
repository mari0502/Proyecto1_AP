package com.example.proyecto1_ap_heyaso;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Preguntas_Foro extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button btnEnviar;
    private TextInputEditText comentario;
    private TextView mostrarUsuario, mostrarTexto, mostrarFecha;
    private LinearLayout primerContenedor;
    private LinearLayout comentarioLayout;
    private String usuario, formattedDate, msg, nombreUsuario;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_foro);
        db = FirebaseFirestore.getInstance();
        recuperarComentarios();

        Button button = (Button) findViewById(R.id.btn_Volver12);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenForo();
            }
        });

        comentario = findViewById(R.id.comentarioPreguntas);
        primerContenedor = findViewById(R.id.contenedorComentarios2);


        btnEnviar =  findViewById(R.id.btnEnviarComentario);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                agregarComentario();
            }
        });

    }

    public void reOpenForo() {
        Intent intent = new Intent(this, Pantalla_Foro.class);
        startActivity(intent);
    }


    private void recuperarComentarios(){
        db.collection("Comentarios")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Escuchando fallido", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                //Recuperar datos
                                Map<String, Object> datos = dc.getDocument().getData();
                                //Fecha y hora
                                Timestamp timestamp = (Timestamp) datos.get("timestamp");
                                if (timestamp != null) {
                                    Date date = timestamp.toDate(); //Convierte el Timestamp a Date
                                    formattedDate = sdf.format(date);  //Formatea la Date a una cadena legible
                                } else {
                                    LocalDateTime now = LocalDateTime.now();
                                    System.out.println("Fecha y hora actual sin formato: " + now);

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
                                    String formattedNow = now.format(formatter);
                                    System.out.println("Fecha y hora actual con formato: " + formattedNow);
                                    formattedDate = formattedNow;
                                }
                                nombreUsuario = datos.get("usuario")+": "; //Usuario
                                msg = datos.get("mensaje").toString()+ " ";

                                // Crea un nuevo LinearLayout para este comentario
                                comentarioLayout = new LinearLayout(Preguntas_Foro.this);
                                comentarioLayout.setOrientation(LinearLayout.HORIZONTAL);

                                // Crea los TextViews para este comentario
                                mostrarUsuario = new TextView(Preguntas_Foro.this);
                                mostrarTexto = new TextView(Preguntas_Foro.this);
                                mostrarFecha = new TextView(Preguntas_Foro.this);

                                //Personalizar TextViews
                                mostrarUsuario.setTextColor(Color.BLACK);
                                mostrarUsuario.setTextSize(12);
                                mostrarUsuario.setTypeface(null, Typeface.BOLD);
                                mostrarTexto.setTextSize(12);
                                mostrarTexto.setTextColor(Color.BLACK);
                                mostrarFecha.setTextSize(5);
                                mostrarFecha.setTextColor(Color.LTGRAY);

                                //Agregar el texto
                                mostrarUsuario.setText(nombreUsuario);
                                mostrarTexto.setText(msg);
                                mostrarFecha.setText(formattedDate);

                                //Añadir los TextViews al LinearLayout
                                comentarioLayout.addView(mostrarUsuario);
                                comentarioLayout.addView(mostrarTexto);
                                comentarioLayout.addView(mostrarFecha);

                                //Añade el LinearLayout al contenedor de los comentarios
                                primerContenedor.addView(comentarioLayout);
                            }
                        }
                    }
                });
    }

    private void agregarComentario(){
        if(!comentario.getText().toString().isEmpty()){
            //Crea el objeto a agregar
            Map<String, Object> datos = new HashMap<>();
            datos.put("mensaje", comentario.getText().toString());
            datos.put("usuario", usuario);
            datos.put("timestamp", FieldValue.serverTimestamp());

            // Inserta a la base
            db.collection("Comentarios").add(datos)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Mensaje enviado con ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error enviando mensaje", e);
                        }
                    });
            comentario.setText("");
        }else {
            Toast.makeText(Preguntas_Foro.this, "Debe ingresar algún comentario.", Toast.LENGTH_SHORT).show();
        }
    }

}