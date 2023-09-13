package com.example.proyecto1_ap_heyaso;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Pantalla_QR extends AppCompatActivity{

    private ImageView qr;
    private Bitmap bitmap;
    private File rutaArchivo;
    private String nombreEstudiante, correoEstudiante, nombreArchivo;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_qr);

        Bundle extras = getIntent().getExtras();
        nombreEstudiante = extras.getString("estudiante");
        correoEstudiante = extras.getString("correo");
        //falta recibir datos

        qr = (ImageView) findViewById(R.id.imagenQR);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap("Estudiante: "+nombreEstudiante,
                    BarcodeFormat.QR_CODE, 181, 181);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        qr.setImageBitmap(bitmap);

        guardarImagenQR();
        enviarQR();
        enviarConfirmacion();
    }

    private void guardarImagenQR(){
        nombreArchivo = "QR_Inscripcion_"+nombreEstudiante+".jpg"; // Nombre del archivo

        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        // Ruta al directorio 'data/data/<tu-app>/app_data/imageDir'
        File directorio = cw.getDir("imagenesQR", Context.MODE_PRIVATE);

        // Crea el archivo
        rutaArchivo = new File(directorio, nombreArchivo);

        try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void enviarQR(){
        NotificacionAutomatica notificacion = new NotificacionAutomatica(Pantalla_QR.this, rutaArchivo, nombreArchivo, "Inscripción al Evento",
                "Su solicitud fue admitida. Con este QR podrá ingresar al evento",correoEstudiante);
        notificacion.execute();
    }

    private void enviarConfirmacion(){
        NotificacionAutomatica confirmacion = new NotificacionAutomatica(Pantalla_QR.this, "Confirmación al Evento",
                "Bienvenido a HeyAso. Se le informa que su inscripción al evento ... ha sido confirmada", correoEstudiante);
    }

}
