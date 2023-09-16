package com.example.proyecto1_ap_heyaso;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;


public class Pantalla_Modificacion_QR extends AppCompatActivity{
    private ImageView qr;
    private Bitmap bitmap;
    private File rutaArchivo;
    private String titulo,fecha, lugar, nombreArchivo;
    private TextView tituloEvento, fechaEvento, lugarEvento;
    private Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificacion_qr);

        Bundle extras = getIntent().getExtras();
        titulo = extras.getString("tituloEvento");
        fecha = extras.getString("fechaEvento");
        lugar = extras.getString("lugarEvento");

        tituloEvento = findViewById(R.id.nombreEventoModificar);
        fechaEvento = findViewById(R.id.fechaEventoModificar);
        lugarEvento = findViewById(R.id.lugarEventoModificar);
        btn_back = findViewById(R.id.btn_volver16);

        qr = (ImageView) findViewById(R.id.imagenModificacion_QR);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap("Nombre del evento: "+ titulo +"Fecha: "+fecha+"Lugar: "+lugar,
                    BarcodeFormat.QR_CODE, 181, 181);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla_Modificacion_QR.this, Administrar_Evento.class);
                startActivity(intent);
            }
        });

        //Setea elementos
        qr.setImageBitmap(bitmap);
        tituloEvento.setText("Nombre del Evento: "+ titulo);
        fechaEvento.setText("Fecha: "+fecha);
        lugarEvento.setText("Lugar: "+lugar);

        guardarImagenQR();
        enviarQR();
    }

    private void guardarImagenQR(){
        nombreArchivo = "QR_Modificacion.jpg"; // Nombre del archivo

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
        NotificacionAutomatica notificacion = new NotificacionAutomatica(Pantalla_Modificacion_QR.this, rutaArchivo, nombreArchivo, "Actualización de Evento",
                "Se ha realizado cambios en el evento "+ titulo);
        notificacion.execute();
    }

}
