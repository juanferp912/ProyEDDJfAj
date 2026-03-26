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
            int cargados = 0;
            int duplicados = 0;
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    Contacto c = new Contacto(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                    
                    // Verificar si el contacto ya existe
                    LinkedList<Contacto> existentes = arbol.buscar(c.nombre);
                    boolean existe = false;
                    
                    for (Contacto ex : existentes) {
                        if (ex.nombre.equals(c.nombre) && ex.apellido.equals(c.apellido) && ex.apodo.equals(c.apodo)) {
                            existe = true;
                            duplicados++;
                            break;
                        }
                    }
                    
                    if (!existe) {
                        insertarContacto(c);
                        cargados++;
                    }
                }
            }
            
            System.out.println("Cargados: " + cargados + " | Duplicados evitados: " + duplicados);
        } catch (IOException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }
    }
}
