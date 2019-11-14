package mp.tareas.tarea6;

public class Arbol
{
    private String key;
    private Arbol left, right;
    private  String resultado="";


    public Arbol(String key)
    {
        this.key = key;
    }

    public Object get( String key )
    {
        if (this.key.equals(key))
        {
            return key;
        }

        if (key.compareTo(this.key) < 0 )
        {
            return left == null ? null : left.get(key);
        }
        else
        {
            return right == null ? null : right.get(key);
        }
    }

    private void nav (Arbol arbol)
    {
        if (arbol != null)
        {
            nav (arbol.left);
            resultado = resultado + arbol.key + "-";
            nav (arbol.right);
        }
    }

    public void print ()
    {
        nav (this);
    }

    public void put(String key)
    {
        if (key.compareTo(this.key) < 0)
        {
            if (left != null)
            {
                left.put(key);
            }
            else
            {
                left = new Arbol(key);
            }
        }
        else if (key.compareTo(this.key) > 0)
        {
            if (right != null)
            {
                right.put(key);
            }
            else
            {
                right = new Arbol(key);
            }
        }
        else
        {
            this.key = key;
        }
    }

    public String getOrder()
    {
        print();
        return resultado;
    }
}