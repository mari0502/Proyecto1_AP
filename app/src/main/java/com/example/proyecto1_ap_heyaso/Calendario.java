package com.example.proyecto1_ap_heyaso;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Locale;
import java.util.Optional;


public class Calendario extends AppCompatActivity{

    private FirebaseFirestore db;
    //Variables para mostrar en calendario
    private MaterialCalendarView calendarView;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy", Locale.US);
    private List<CalendarDay> fechas = new ArrayList<>();
    private List<CalendarDay> fechasDeInteres = new ArrayList<>();
    private puntosCalendario marcarFechasEventos;
    private Date fechaCalendario;
    private CalendarDay fechaObtenida;
    private desactivarFecha desactivarFecha;
    //Evento y sus variables
    private Evento evento;
    private String idAsociacion, idEvento, titulo, categoria,descripcion, duracion, fecha, lugar, requisitos;
    private ArrayList<Evento> listaEventos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        db = FirebaseFirestore.getInstance();
        calendarView = findViewById(R.id.vistaCalendario);
        crearCanalNotificaciones();

        Button btnMarcar = (Button) findViewById(R.id.btnMarcar);
        btnMarcar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                marcarEventoInteres();
            }
        });

        Button btnVolver = (Button) findViewById(R.id.btnCalendarioVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Calendario.this, menuPrincipalEstudiante.class);
                startActivity(intent);
            }
        });

        recuperarEventos();
    }

    private void crearCanalNotificaciones(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "canalEventos";
            String description = "Canal para mis notificaciones de eventos";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("miCanal", name, importance);
            channel.setDescription(description);
            // Registrar el canal con el sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void marcarEventoInteres(){
        fechaObtenida = calendarView.getSelectedDate();
        if (fechaObtenida != null) {
            if(fechaConEvento() == true){
                //Deshabilitar fecha
                desactivarFecha = new desactivarFecha(fechaObtenida);
                //Añadir decorador al MaterialCalendarView
                calendarView.addDecorator(desactivarFecha);

                Toast.makeText(getApplicationContext(), "Se ha marcado el evento de su interés.", Toast.LENGTH_LONG).show();
                establecerRecordatorio();
            }else{
                Toast.makeText(getApplicationContext(), "La fecha indicada no tiene ningún evento por el momento.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No ha seleccionado ninguna fecha.", Toast.LENGTH_LONG).show();
        }
    }


    private boolean fechaConEvento(){
        for (Evento e: listaEventos) {
            if(e.getFecha().equals(formatoFecha.format(fechaObtenida.getDate()))){
                return true;
            }
        }
        return false;
    }

    private void establecerRecordatorio(){
        //Crear un intent para el BroadcastReceiver
        Intent intent = new Intent(this, recordatorioEvento.class);

        //Crear un PendingIntent que se activará cuando se dispare la alarma
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //Obtener una instancia de AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Establecer la alarma
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, fechaObtenida.getYear());
        calendar.set(Calendar.MONTH, fechaObtenida.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, fechaObtenida.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, 10); // Establece la hora del día (formato de 24 horas)
        calendar.set(Calendar.MINUTE, 20); // Establece los minutos

        //Crear un AlarmClockInfo
        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent);

        //Establecer la alarma
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
    }
    private void recuperarEventos(){
        db.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cargarDatos(document.getId().toString());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // Comprobar si la fecha seleccionada está en la lista de fechas
                if (fechas.contains(date)) {
                    for (Evento e: listaEventos) {
                        if(e.getFecha().equals(formatoFecha.format(date.getDate()))){
                            // Crear un AlertDialog para mostrar la información
                            new AlertDialog.Builder(Calendario.this)
                                    .setTitle("Evento: "+ e.getTitulo())
                                    .setMessage("Descripción: "+ e.getDescripcion()
                                            +"\nFecha: "+e.getFecha()+"\nLugar: "+e.getLugar()
                                            +"\nCategoría: "+e.getCategoria()
                                            +"\nDuración: "+e.getDuracion()
                                            +"\nRequisitos: "+e.getRequisitos())
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                        }
                    }
                }

            }
        });
    }

    private void cargarDatos(String id){
        db.collection("Evento").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    //Recupera cada dato de cada evento
                    idAsociacion = documentSnapshot.getString("idAsodiacion");
                    idEvento = documentSnapshot.getString("idEvento");
                    titulo = documentSnapshot.getString("titulo");
                    descripcion  = documentSnapshot.getString("descripcion");
                    fecha = documentSnapshot.getString("fecha");
                    System.out.println(fecha);
                    lugar = documentSnapshot.getString("lugar");
                    categoria = documentSnapshot.getString("categoria");
                    duracion = documentSnapshot.getString("duracion");
                    requisitos = documentSnapshot.getString("requisitos");

                    if(requisitos == ""){
                        requisitos = "No tiene";
                    }

                    //-------- Crea puntos en el calendario ---------------------------
                    try {
                        // Parsear la fecha a un objeto Date
                        fechaCalendario = formatoFecha.parse(fecha);
                        // Convertir el objeto Date a CalendarDay y añadirlo a la lista
                        fechas.add(CalendarDay.from(fechaCalendario));

                        // Crear un nuevo decorador
                        marcarFechasEventos = new puntosCalendario(Color.MAGENTA, fechas);

                        //Añadir el decorador al MaterialCalendarView
                        calendarView.addDecorator(marcarFechasEventos);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
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

                    listaEventos.add(evento);
                }
            }
        });
    }

}