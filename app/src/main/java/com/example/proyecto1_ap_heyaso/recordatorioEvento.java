package com.example.proyecto1_ap_heyaso;


import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;
import java.util.UUID;


public class recordatorioEvento extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "miCanal")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("¡Recordatorio de Evento!")
                .setContentText("Hoy tienes un evento que te interesa. ¡No olvides revisarlo!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Random aleatorio = new Random();
        int id = aleatorio.nextInt(100);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }



}
