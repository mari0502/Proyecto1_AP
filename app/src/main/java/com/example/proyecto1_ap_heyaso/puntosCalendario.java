package com.example.proyecto1_ap_heyaso;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class puntosCalendario implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> dates;
    private int radio;

    //Constructor
    public puntosCalendario(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.radio = 8;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(radio, color)); // Puedes modificar el radio del punto aquí
    }

    //Getters and setters
    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }
}
