package com.example.proyecto1_ap_heyaso;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;


public class Pantalla_Eliminacion_QR extends AppCompatActivity{
    private ImageView qr;
    private Bitmap bitmap;
    private File rutaArchivo;
    private String titulo, nombreArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_eliminacion_qr);


        Bundle extras = getIntent().getExtras();
        //nombreEstudiante = extras.getString("estudiante");
        //correoEstudiante = extras.getString("correo");
        //falta recibir datos

        qr = (ImageView) findViewById(R.id.imagenEliminacion_QR);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap("DatosEliminacionEvento",
                    BarcodeFormat.QR_CODE, 181, 181);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        qr.setImageBitmap(bitmap);

        guardarImagenQR();
        enviarQR();
    }

    private void guardarImagenQR(){
        nombreArchivo = "QR_Cancelacion.jpg"; // Nombre del archivo

        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        // Ruta al directorio
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
        NotificacionAutomatica notificacion = new NotificacionAutomatica(Pantalla_Eliminacion_QR.this, rutaArchivo, nombreArchivo, "Cancelación de Evento",
                "Se ha cancelado el evento");
        notificacion.execute();
    }

}
