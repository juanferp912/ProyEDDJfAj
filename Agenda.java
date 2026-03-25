import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public void cargarContactosDesdeArchivo(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    Contacto c = new Contacto(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                    insertarContacto(c);
                }
            }
            System.out.println("Contactos cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }
    }
}
