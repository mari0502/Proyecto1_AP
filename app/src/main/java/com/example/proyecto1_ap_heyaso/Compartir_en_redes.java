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

import com.google.android.material.textfield.TextInputEditText;

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

    private TextInputEditText descripcion,lugar, tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir_en_redes);

        //asignamos a los botones e la actividad los botones del diseño.
        botonCompartir = (Button) findViewById(R.id.btn_compartir);
        descripcion = findViewById(R.id.descripcion);
        lugar = findViewById(R.id.lugar);
        tags = findViewById(R.id.tags);

        //ahora le asignamos la accion a relizar a los botones
        botonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartir();
            }
        });

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenOpciones_evento();
            }
        });
    }

    private void compartir(){
        if(descripcion.getText().toString().isEmpty() || lugar.getText().toString().isEmpty() || tags.getText().toString().isEmpty()){
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            //Crea un intent con la acción SEND para compartir datos
            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            // Establece el tipo de datos a compartir (en este caso, texto plano)
            shareIntent.setType("text/plain");

            // Añade el texto que quieres compartir
            shareIntent.putExtra(Intent.EXTRA_TEXT, descripcion.getText().toString()+
                    "\n"+tags.getText().toString()+
                    "\n"+"Lugar: "+lugar.getText().toString());

            // Crea un chooser para que el usuario seleccione la aplicación con la que quiere compartir
            Intent chooser = Intent.createChooser(shareIntent, "Compartir con");

            // Verifica si hay alguna aplicación que pueda manejar este intent
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }else{
                NotificacionAutomatica notificacionRedes = new NotificacionAutomatica(this,"Atentos a nuestro proximo evento",
                        descripcion.getText().toString()+
                                "\n"+tags.getText().toString()+
                                "\n"+"Lugar: "+lugar.getText().toString());
                notificacionRedes.execute();
            }
        }
    }

    public void reOpenOpciones_evento() {
        Intent intent = new Intent(this, Opciones_Evento.class);
        startActivity(intent);
    }
}


