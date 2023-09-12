package com.example.proyecto1_ap_heyaso;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificacionAutomatica extends AsyncTask<Void, Void, Void>{
    private Context contenido;
    private Session sesion;
    private String asunto, mensaje, destinario;

    private final String emisor = "oficialheyaso@gmail.com",  contrasenna= "knwwogxjwktttent";

    private Address[] destinarios;

    public NotificacionAutomatica() {
        //Default
    }

    //Constructor para enviar correo a todos los estudiantes
    public NotificacionAutomatica(Context contenido, String asunto, String mensaje) {
        this.contenido = contenido;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinario = "null";
    }

    //Constructor para enviar correo a un solo estudiante
    public NotificacionAutomatica(Context contenido, Session sesion, String asunto, String mensaje, String destinario) {
        this.contenido = contenido;
        this.sesion = sesion;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinario = destinario;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        sesion = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emisor, contrasenna);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(sesion);
        try {
            //Emisor del correo
            mimeMessage.setFrom(new InternetAddress(emisor));

            //Destinatarios
            if( destinario == "null"){
                mimeMessage.addRecipients(Message.RecipientType.TO,destinarios);
            }else{
                mimeMessage.addRecipients(Message.RecipientType.TO,destinario);
            }

            //Asunto
            mimeMessage.setSubject(asunto);

            //Contenido
            mimeMessage.setText(mensaje);

            //Enviar
            Transport.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
