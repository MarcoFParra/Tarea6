package mp.tareas.tarea6;

import android.os.Parcel;
import android.os.Parcelable;

public class Contacto implements Parcelable {
    int edad;
    String nombres, expediente;

    public Contacto(String nombres,int edad,String expediente)
    {
        this.edad = edad;
        this.nombres = nombres;
        this.expediente = expediente;
    }

    protected Contacto(Parcel in) {
        edad = in.readInt();
        nombres = in.readString();
        expediente = in.readString();

    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };


    public String getNombres() {
        return nombres;
    }

    public int getEdad() {
        return edad;
    }

    public String getExpediente() {
        return expediente;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(edad);
        dest.writeString(nombres);
        dest.writeString(expediente);
    }
}
