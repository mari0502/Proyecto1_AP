package com.example.proyecto1_ap_heyaso;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;


public class Calendario extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        MaterialCalendarView calendarView = findViewById(R.id.vistaCalendario);

        // Establecer un selector de fecha
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // Mostrar un diálogo
                System.out.println(date.toString());
                if(date.toString() == "2023-8-3"){
                    new AlertDialog.Builder(widget.getContext())
                            .setTitle("Un evento")
                            .setPositiveButton("Marcar", null)
                            .show();
                }else{
                    new AlertDialog.Builder(widget.getContext())
                            .setTitle("Fecha seleccionada")
                            .setMessage(date.toString())
                            .setPositiveButton("OK", null)
                            .show();
                }

            }
        });

        // Marcar una fecha
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return day.equals(CalendarDay.today());
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, Color.RED));
            }
        });

    }
}
