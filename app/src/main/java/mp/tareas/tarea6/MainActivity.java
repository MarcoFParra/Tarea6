package mp.tareas.tarea6;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST = 200;

    ContactosAdapter adapter = null;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter  = new ContactosAdapter(this, 0);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ContactActivity.class));
            }
        });
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        createFolder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume()
    {
        super.onResume();

        File[] files = new File( getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath() + "/expedientes").listFiles();
        if (files.length>0) {
            Arbol arbol = new Arbol(files[0].getName().replace(".txt", ""));
            if (files.length > 1) {
                for (File file : files) {
                    arbol.put(file.getName().replace(".txt", ""));
                }
            }
            String orden = arbol.getOrder();
            orden.length();
            adapter.addElemnts(orden.split("-"));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createFolder();
                } else {
                }
                return;
            }
        }
    }

    public  void createFolder() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
            }
        } else {
            File f = new File( getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath(), "/expedientes");
            if (!f.exists())
                if (!f.mkdir()) {
                    //Toast.makeText(this, f.getAbsolutePath() + " can't be created.", Toast.LENGTH_SHORT).show();

                }

        }
    }

}
