import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Agenda {
    APrefijo<Contacto> arbol;
    LinkedList<Contacto> todosContactos;

    public Agenda() {
        arbol = new APrefijo<>();
        todosContactos = new LinkedList<>();
    }

    public void insertarContacto(Contacto c) {
        arbol.insertar(c.nombre, c);
        arbol.insertar(c.apellido, c);
        arbol.insertar(c.apodo, c);
        todosContactos.add(c);
    }

    public void eliminarContacto(Contacto c) {
        arbol.eliminar(c.nombre);
        arbol.eliminar(c.apellido);
        arbol.eliminar(c.apodo);
        todosContactos.remove(c);
    }
    
    public String eliminarContactoPorDatos(String nombre, String apellido, String apodo) {
        // Buscar el contacto en todosContactos
        for (Contacto c : todosContactos) {
            if (c.nombre.toLowerCase().equals(nombre.toLowerCase()) && 
                c.apellido.toLowerCase().equals(apellido.toLowerCase()) && 
                c.apodo.toLowerCase().equals(apodo.toLowerCase())) {
                // Encontrado, eliminar
                arbol.eliminar(c.nombre);
                arbol.eliminar(c.apellido);
                arbol.eliminar(c.apodo);
                todosContactos.remove(c);
                return "Contacto eliminado exitosamente.";
            }
        }
        return "Contacto no encontrado.";
    }
    
    public LinkedList<Contacto> obtenerContactosUnicos() {
        LinkedList<Contacto> unicos = new LinkedList<>();
        for (Contacto c : todosContactos) {
            boolean existe = false;
            for (Contacto u : unicos) {
                if (u.nombre.equals(c.nombre) && u.apellido.equals(c.apellido) && u.apodo.equals(c.apodo)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                unicos.add(c);
            }
        }
        return unicos;
    }

    public LinkedList<Contacto> buscar(String palabra) {
        return arbol.findPrefix(palabra);
    }

    public String cargarContactosDesdeArchivo(String ruta) {
        todosContactos.clear();
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
            
            String msg = "Cargados: " + cargados + " | Duplicados evitados: " + duplicados;
            System.out.println(msg);
            return msg;
        } catch (IOException e) {
            String errMsg = "Error al cargar archivo: " + e.getMessage();
            System.out.println(errMsg);
            return errMsg;
        }
    }

    public String exportarContactosAArchivo(String ruta) {
        try (FileWriter fw = new FileWriter(ruta)) {
            LinkedList<Contacto> contactosExportar = obtenerContactosUnicos();
            if (contactosExportar.isEmpty()) {
                String msg = "No hay contactos para exportar.";
                System.out.println(msg);
                return msg;
            }
            for (Contacto c : contactosExportar) {
                fw.write(c.nombre + "," + c.apellido + "," + c.apodo + "," + 
                         c.telefonoMovil + "," + c.telefonoConvencional + "," + c.correoElectronico + "\n");
            }
            
            String msg = "Se exportaron " + contactosExportar.size() + " contactos al archivo.";
            System.out.println(msg);
            return msg;
        } catch (IOException e) {
            String errMsg = "Error al exportar: " + e.getMessage();
            System.out.println(errMsg);
            return errMsg;
        }
    }
}
