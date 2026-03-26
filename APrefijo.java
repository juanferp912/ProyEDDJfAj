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

    public LinkedList<E> findPrefix(String prefijo) {
        LinkedList<E> resultado = new LinkedList<>();
        APrefijo<E> actual = this;

        for (int i = 0; i < prefijo.length(); i++) {
            char letra = prefijo.charAt(i);
            actual = actual.hijos.get(letra);

            if (actual == null) {
                return resultado;
            }
        }
        recolectar(actual, resultado);

        return resultado;
    }

    private void recolectar(APrefijo<E> nodo, LinkedList<E> resultado) {
        if (nodo.finNombre) {
            resultado.addAll(nodo.datos);
        }
        for (Character c : nodo.hijos.keySet()) {
            recolectar(nodo.hijos.get(c), resultado);
        }
    }

    public LinkedList<E> obtenerTodos() {
        LinkedList<E> todos = new LinkedList<>();
        recolectar(this, todos);
        return todos;
    }

    public void eliminar(String palabra) {
        eliminarRec(palabra, 0);
    }

    private boolean eliminarRec(String palabra, int i) {
        if (i == palabra.length()) {
            if (!finNombre) {
                return false;
            }

            finNombre = false;
            datos.clear();

            return hijos.isEmpty();
        }

        char letra = palabra.charAt(i);
        APrefijo<E> hijo = hijos.get(letra);

        if (hijo == null) {
            return false;
        }

        boolean debeEliminarHijo = hijo.eliminarRec(palabra, i + 1);

        if (debeEliminarHijo) {
            hijos.remove(letra);
            return hijos.isEmpty() && !finNombre;
        }

        return false;
    }
}
