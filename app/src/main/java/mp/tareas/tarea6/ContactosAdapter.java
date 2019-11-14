package mp.tareas.tarea6;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ContactosAdapter extends ArrayAdapter<Contacto> {
    private Activity activity;
    private View item;
    private LayoutInflater inflater;

    public ContactosAdapter(@NonNull Activity activity, int resource) {
        super(activity, resource);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        inflater = activity.getLayoutInflater();

        try {
             item = inflater.inflate(R.layout.item_contacto, parent, false);


            ((TextView) item.findViewById(R.id.tv_nombre)).setText(getItem(position).getNombres());
            String titulo="";
            try {
                int cont = 0;
                for (String text:getItem(position).getNombres().split(" ")) {
                    cont ++;
                    titulo = titulo + String.valueOf(text.charAt(0));
                    if (cont == 2)
                        break;;
                }
            }catch (Exception ex){}
            ((TextView) item.findViewById(R.id.tv_titulo)).setText(titulo);

            ((TextView) item.findViewById(R.id.tv_edad)).setText("Edad: "+ getItem(position).getEdad());
            item.setClickable(true);
            item.setFocusable(true);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ContactActivity.class).putExtra("CONTACTO", getItem(position)));
                }
            });

            item.findViewById(R.id.image_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(getItem(position).getNombres());
                    Toast.makeText(activity,"Contacto Eliminado correctamente",Toast.LENGTH_SHORT).show();
                    activity.recreate();
                }
            });
        }catch (Exception ex){
            Toast.makeText(activity,ex.toString(),Toast.LENGTH_SHORT).show();

        }

            return item;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private String open(String fileName)
    {

        File file = new File(activity.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath() + "/expedientes", fileName + ".txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    private void delete(String fileName)
    {
        File file = new File(activity.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath() + "/expedientes", fileName + ".txt");
        file.delete();

    }

    public void addElemnts(String[] split) {
        this.clear();
        int edad=0;
        String expediente="";
        for (String persona:split) {
            String[] res = open(persona).split("°");
             edad = Integer.valueOf(res[0]);
             expediente = res[1];
            this.add(new Contacto(persona,edad,expediente));
        }
        this.notifyDataSetChanged();
    }
}
