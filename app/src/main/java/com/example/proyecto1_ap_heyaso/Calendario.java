package com.example.proyecto1_ap_heyaso;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Calendario extends AppCompatActivity{

    private FirebaseFirestore db;
    private MaterialCalendarView calendarView;

    private String idAsociacion, idEvento, titulo, categoria,descripcion, duracion, fecha, lugar, requisitos;
    private Boolean encuesta;
    private ArrayList<Evento> listaEventos = new ArrayList<>();
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
    private Date fechaCalendario;
    private CalendarDay fechaEvento;
    private List<CalendarDay> fechas = new ArrayList<>();
    private List<DayViewDecorator> decorators = new ArrayList<>();
    private Evento evento;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        db = FirebaseFirestore.getInstance();

       calendarView = findViewById(R.id.vistaCalendario);
       recuperarEventos();

       for (int i = 0; i < listaEventos.size(); i++) {
            if(listaEventos.isEmpty()){
                System.out.println("No hay eventos");
            }else{
                System.out.println(listaEventos.get(i).getFecha());
            }
        }
    }

    private void recuperarEventos(){
        db.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        cargarDatos(document.getId().toString());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void cargarDatos(String id){
        System.out.println("ENTRO CARGAR DATOS");
        db.collection("Evento").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    System.out.println("EXISTE DOCUMENTO");
                    //Recupera cada dato de cada evento
                    idAsociacion = documentSnapshot.getString("idAsodiacion");
                    idEvento = documentSnapshot.getString("idEvento");
                    titulo = documentSnapshot.getString("titulo");
                    System.out.println(titulo);
                    descripcion  = documentSnapshot.getString("descripcion");
                    fecha = documentSnapshot.getString("fecha");
                    lugar = documentSnapshot.getString("lugar");
                    categoria = documentSnapshot.getString("categoria");
                    duracion = documentSnapshot.getString("duracion");
                    requisitos = documentSnapshot.getString("requisitos");
                    //encuesta = documentSnapshot.getBoolean("encuesta");
                    //String encuestaStr = Boolean.toString(encuesta);


                    if(requisitos == ""){
                        requisitos = "No tiene";
                    }

                    //Crea el evento
                    evento = new Evento();
                    //Setea los valores del objeto evento
                    evento.setIdAsociacion(idAsociacion);
                    evento.setIdEvento(idEvento);
                    evento.setTitulo(titulo);
                    evento.setDescripcion(descripcion);
                    evento.setFecha(fecha);
                    evento.setLugar(lugar);
                    evento.setCategoria(categoria);
                    evento.setDuracion(duracion);
                    evento.setRequisitos(requisitos);
                    //evento.setEncuesta(encuestaStr);

                    System.out.println("Agrega el Evento");
                    listaEventos.add(evento);
                }
            }
        });

        System.out.println("SALE DE CARGAR DATOS");

    }

    private void mostrarInfo(){
        System.out.println("ENTRO A MOSTRAR INFO");

        for (int i = 0; i < listaEventos.size(); i++) {
            System.out.println("FECHA RECUPERADA: " + listaEventos.get(i).getFecha().toString());
        }
        //fechaCalendario = formatoFecha.parse();
                       /*
        for (int i = 0; i < listaEventos.size(); i++) {
            System.out.println("ENTROO FOR");
            try {
                System.out.println("ENTRO AL TRY");

                System.out.println(fechaCalendario);
                fechaEvento = CalendarDay.from(fechaCalendario);
                System.out.println(fechaEvento);
                decorators.add(new DayViewDecorator() {
                    @Override
                    public boolean shouldDecorate(CalendarDay day) {
                        System.out.println("ENTRA A COMPARAR");
                        return fechaEvento.equals(day);
                    }

                    @Override
                    public void decorate(DayViewFacade view) {
                        System.out.println("PINTA EN EL CALENDARIO");
                        view.addSpan(new DotSpan(5, Color.RED));
                    }
                });
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        calendarView.addDecorators(decorators);




        //Marcar varias fechas
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                for (int i = 0; i < listaEventos.size(); i++) {
                    //Convierte String a CalendarDay
                    try {

                        //Compara fechas
                        if (day.equals(CalendarDay.from(fechaCalendario))) {
                            return true;
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, Color.RED));
            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                // Aquí puedes filtrar tus datos por la fecha seleccionada.
                // Por ejemplo, puedes mostrar un Toast con la fecha.
                String selectedDate = String.valueOf(DateFormat.format("dd/MM/yyyy", date.getDate()));
                Toast.makeText(getApplicationContext(), "Fecha seleccionada: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
