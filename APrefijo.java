import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class APrefijo<E> {
    Map<Character, APrefijo<E>> hijos;
    boolean finNombre;
    LinkedList<E> datos;

    public APrefijo() {
        hijos = new HashMap<>();
        finNombre = false;
        datos = new LinkedList<>();
    }

    public void insertar(String palabra, E dato) {
        insertarRec(palabra, dato, 0);
    }

    private void insertarRec(String palabra, E dato, int i) {
        if (i == palabra.length()) {
            finNombre = true;
            datos.add(dato);
            return;
        }

        char letra = palabra.charAt(i);
        APrefijo<E> hijo = hijos.get(letra);

        if (hijo == null) {
            hijo = new APrefijo<>();
            hijos.put(letra, hijo);
        }
        hijo.insertarRec(palabra, dato, i + 1);
    }

    public LinkedList<E> buscar(String palabra) {
        APrefijo<E> actual = this;

        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            APrefijo<E> hijo = actual.hijos.get(letra);
            if (hijo == null) {
                return new LinkedList<>();
            }
            actual = hijo;
        }
        if (actual.finNombre) {
            return actual.datos;
        }

        return new LinkedList<>();
    }

    public LinkedList<E> findPrefix(String s) {
        LinkedList<E> resultado = new LinkedList<>();
        int i = 0;
        APrefijo<E> p = this;

        while (i < s.length()) {
            p = p.hijos.get(s.charAt(i));
            if (p == null) {
                return new LinkedList<>();
            }
            i++;
        }

        p.recorrePrefifo(resultado);
        return resultado;
    }

    private void recorrePrefifo(LinkedList<E> lResultado) {
        if (finNombre) {
            lResultado.addAll(datos);
        }
        for (Map.Entry<Character, APrefijo<E>> entry : hijos.entrySet()) {
            APrefijo<E> hijo = entry.getValue();
            hijo.recorrePrefifo(lResultado);
        }
    }
}
