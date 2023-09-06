package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Compartir_en_redes extends AppCompatActivity {

    //creamos los botones paracompartir
    private Button botonCompartir;
    private ImageButton botonFacebook;
    private ImageButton botonInstagram;


    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir_en_redes);

        //asignamos a los botones e la actividad los botones del diseño.
        //el error se corregir al parsear
        botonCompartir = (Button) findViewById(R.id.btn_compartir);
        botonFacebook = (ImageButton) findViewById(R.id.Facebook);
        botonInstagram = (ImageButton) findViewById(R.id.Instagram);


        //ahora le asignamos la accion a relizar a los botones
        botonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Atentos a nuestro proximo evento");
                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

        botonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Atentos a nuestro proximo evento");
                intent.setPackage("com.facebook.katana");
                startActivity(intent);
            }
        });

        botonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Atentos a nuestro proximo evento");
                //Para especificar la red social especifica se le asigna en esta parte
                intent.setPackage("com.instagram.android");
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenOpciones_evento();
            }
        });
    }


    public void reOpenOpciones_evento() {
        Intent intent = new Intent(this, Opciones_Evento.class);
        startActivity(intent);
    }
}


