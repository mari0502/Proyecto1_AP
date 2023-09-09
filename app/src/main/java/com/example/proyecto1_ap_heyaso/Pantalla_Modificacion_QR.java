package com.example.proyecto1_ap_heyaso;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class Pantalla_Modificacion_QR extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificacion_qr);

        ImageView qr = (ImageView) findViewById(R.id.imagenModificacion_QR);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap("DatosModificacionEvento",
                    BarcodeFormat.QR_CODE, 181, 181);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        qr.setImageBitmap(bitmap);
    }
}
