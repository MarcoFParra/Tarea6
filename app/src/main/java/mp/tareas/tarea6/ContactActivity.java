package mp.tareas.tarea6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ContactActivity extends AppCompatActivity {
    Contacto contacto = null;
    FloatingActionButton fab;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            contacto = getIntent().getParcelableExtra("CONTACTO");
        }catch (Exception ex){
            contacto = null;
        }

        setContentView(R.layout.activity_contacto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*ContactosAdapter.Items.add(new Contacto(ContactosAdapter.Items.size(), ((EditText)findViewById(R.id.et_Nombres)).getText().toString(),
                        ((EditText)findViewById(R.id.et_Apellidos)).getText().toString(),
                        ((EditText)findViewById(R.id.et_Direccion)).getText().toString(),
                        ((EditText)findViewById(R.id.et_Telefono)).getText().toString(),
                        ((EditText)findViewById(R.id.et_Celular)).getText().toString()));*/

                writeToFile();
                ContactActivity.this.finish();
            }

        });

        if (contacto != null)
        {
            fab.setVisibility(View.GONE);
            ((TextView)findViewById(R.id.tv_Nombres)).setText(contacto.getNombres());
            ((TextView)findViewById(R.id.tv_Telefono)).setText(String.valueOf(contacto.getEdad()));
            ((TextView)findViewById(R.id.tv_exp)).setText(contacto.getExpediente());

            ((ViewSwitcher)findViewById(R.id.my_switcher_Nombres)).showNext();
            ((ViewSwitcher)findViewById(R.id.my_switcher_telefono)).showNext();
            ((ViewSwitcher)findViewById(R.id.my_switcher_exp)).showNext();
        }
        else
        {
        }
    }

    boolean isExternalStorageAvailable()
    {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    File file;
    private static final int MY_PERMISSIONS_REQUEST = 200;

    private void writeToFile() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
            }
        } else {
            if (isExternalStorageAvailable()) {
                FileOutputStream fos = null;
                file = new File(getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath() +"/expedientes", ((EditText)findViewById(R.id.et_Nombres)).getText().toString() + ".txt");
                try {
                    fos = new FileOutputStream(file);
                    fos.write( (((EditText)findViewById(R.id.et_Telefono)).getText().toString() + "°" +
                            (((EditText)findViewById(R.id.et_exp)).getText().toString())).getBytes());
                    fos.close();
                    //Toast.makeText(this, file.getAbsolutePath()  + "Archivo guardado correctamente", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, file.getAbsolutePath()  + "Error", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, file.getAbsolutePath()  + "Error", Toast.LENGTH_SHORT).show();

                }

            }

        }
    }
}
