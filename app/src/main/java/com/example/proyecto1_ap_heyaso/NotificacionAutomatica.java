package com.example.proyecto1_ap_heyaso;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificacionAutomatica {
    private String asunto, contenido, usuario, contrasenna;
    private Address[] destinarios;

    public NotificacionAutomatica() {
        //Default
    }

    public NotificacionAutomatica(String asunto, String contenido, String usuario, String contrasenna) {
        this.asunto = asunto;
        this.contenido = contenido;
        this.usuario = usuario;
        this.contrasenna = contrasenna;


    }

    private void contruirCorreo(){
        //Inicializar propiedades
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.estudiantec.cr");
        properties.put("mail.smtp.port", "587");

        //Inicializar sesión
        Session sesion = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contrasenna);
            }
        });

        try{
            //Inicializar contenido del correo
            Message mensaje = new MimeMessage(sesion);

            //Emisor del correo
            mensaje.setFrom(new InternetAddress(usuario));

            //Destinario del correo
            mensaje.addRecipients(Message.RecipientType.TO, destinarios); //Revisar el tipo Address

            //Asunto del correo
            mensaje.setSubject(asunto);

            //Contenido del correo
            mensaje.setText(contenido);

            //Enviar correo

        }catch (MessagingException e){
            e.printStackTrace();
        }

    }

    /*
    private class SendMail extends AsyncTask<Message, String, String>{
        //Inicializar progress dialog
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Crear y mostrar el progress dialog
            progressDialog = ProgressDialog.show(Propuesta_Evento,
                    "Por favor espere", "Enviando correo...");
        }
        @Override
        protected String doInBackground(Message... messages) {
            return null;
        }
    }*/
}
