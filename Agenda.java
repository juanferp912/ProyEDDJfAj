import java.util.LinkedList;

public class Agenda {
    APrefijo<Contacto> arbol;

    public Agenda() {
        arbol = new APrefijo<>();
    }

    public void insertarContacto(Contacto c) {
        arbol.insertar(c.nombre, c);
        arbol.insertar(c.apellido, c);
        arbol.insertar(c.apodo, c);
    }

    public void eliminarContacto(Contacto c) {
        arbol.eliminar(c.nombre);
        arbol.eliminar(c.apellido);
        arbol.eliminar(c.apodo);
    }

    public LinkedList<Contacto> buscar(String palabra) {
        return arbol.findPrefix(palabra);
    }
}
