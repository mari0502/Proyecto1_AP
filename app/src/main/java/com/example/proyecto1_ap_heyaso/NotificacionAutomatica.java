package com.example.proyecto1_ap_heyaso;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class NotificacionAutomatica extends AsyncTask<Void, Void, Void>{

    private FirebaseFirestore db;
    private Context contenido;
    private Session sesion;
    private String asunto, mensaje, destinario, nombreArchivo;

    private final String emisor = "oficialheyaso@gmail.com",  contrasenna= "knwwogxjwktttent";

    private  List<String>  destinarios = new ArrayList<String>();
    private File rutaArchivo;
    private Boolean imagen;


    //Constructor para enviar correo a TODOS los estudiantes (modificaciones, eliminaciones)
    public NotificacionAutomatica(Context contenido, File rutaArchivo, String nombreArchivo, String asunto, String mensaje) {
        this.contenido = contenido;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinario = "null";
        this.imagen = false;
        this.rutaArchivo = rutaArchivo;
        this.nombreArchivo = nombreArchivo;
    }

    //Constructor para enviar correo a un solo estudiante, SIN IMAGEN
    public NotificacionAutomatica(Context contenido, String asunto, String mensaje, String destinario) {
        this.contenido = contenido;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinario = destinario;
        this.imagen = false;
    }

    //Constructor para enviar correo a un SOLO estudiante con IMAGEN
    public NotificacionAutomatica(Context contenido, File rutaArchivo, String nombreArchivo, String asunto, String mensaje, String destinario) {
        this.contenido = contenido;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinario = destinario;
        this.imagen = true;
        this.rutaArchivo = rutaArchivo;
        this.nombreArchivo = nombreArchivo;
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

        try {
            Message message = new MimeMessage(sesion);

            //Emisor del correo
            message.setFrom(new InternetAddress(emisor));

            //Destinatarios
            if( destinario == "null"){
                agregarDestinatarios();
                for (int i = 0; i < destinarios.size(); i++) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinarios.get(i)));
                }
            }else{
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinario));
            }

            //Asunto
            message.setSubject(asunto);

            if(this.imagen == false){
                //Cotenido
                message.setText(mensaje);
                //Enviar solo texto
                Transport.send(message);
            }else{
                // Crea el cuerpo del mensaje
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(mensaje);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // Adjunta la imagen
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(rutaArchivo);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(nombreArchivo);
                multipart.addBodyPart(messageBodyPart);

                // Envia el correo electrónico
                message.setContent(multipart);

                //Enviar con imagen
                Transport.send(message);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void agregarDestinatarios(){
        db.collection("usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        recuperarCorreo(document.getId().toString());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void recuperarCorreo(String id){
        db.collection("usuario").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    destinarios.add(documentSnapshot.getString("contacto"));
                }
            }
        });
    }
}
