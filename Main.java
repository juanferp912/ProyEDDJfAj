import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static Agenda agenda = new Agenda();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        
        while (true) {
            try {
                System.out.println("\n____AGENDA DE CONTACTOS___");
                System.out.println("1. Cargar contactos desde archivo");
                System.out.println("2. Insertar contacto");
                System.out.println("3. Buscar por prefijo");
                System.out.println("4. Eliminar contacto");
                System.out.println("5. Exportar contactos a archivo");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        cargarArchivo();
                        break;
                    case 2:
                        insertarContacto();
                        break;
                    case 3:
                        buscarContacto();
                        break;
                    case 4:
                        eliminarContacto();
                        break;
                    case 5:
                        exportarContactos();
                        break;
                    case 6:
                        System.out.println("¡Hasta luego!");
                        System.exit(0);
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese datos válidos.");
                sc.nextLine();
            }
        }
    }

    static void cargarArchivo() {
        try {
            System.out.print("Ingrese la ruta del archivo (ej: contactos.txt): ");
            String ruta = sc.nextLine();
            if (ruta.isEmpty()) {
                System.out.println("La ruta no puede estar vacía.");
                return;
            }
            String resultado = agenda.cargarContactosDesdeArchivo(ruta);
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("Error al cargar archivo.");
        }
    }

    static void insertarContacto() {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Apellido: ");
            String apellido = sc.nextLine();
            System.out.print("Apodo: ");
            String apodo = sc.nextLine();
            System.out.print("Teléfono móvil: ");
            String movil = sc.nextLine();
            System.out.print("Teléfono convencional: ");
            String convencional = sc.nextLine();
            System.out.print("Correo electrónico: ");
            String email = sc.nextLine();

            if (nombre.isEmpty() || apellido.isEmpty() || apodo.isEmpty()) {
                System.out.println("Los campos nombre, apellido y apodo no pueden estar vacíos.");
                return;
            }

            Contacto c = new Contacto(nombre, apellido, apodo, movil, convencional, email);
            agenda.insertarContacto(c);
            System.out.println("Contacto insertado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar contacto.");
        }
    }

    static void buscarContacto() {
        try {
            System.out.print("Ingrese prefijo a buscar: ");
            String prefijo = sc.nextLine();
            if (prefijo.isEmpty()) {
                System.out.println("El prefijo no puede estar vacío.");
                return;
            }
            LinkedList<Contacto> resultados = agenda.buscar(prefijo);

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron contactos.");
            } else {
                // Incrementar frecuencia de cada contacto encontrado
                for (Contacto c : resultados) {
                    c.frecuencia++;
                }
                
                // Ordenar por frecuencia (burbuja sort)
                for (int i = 0; i < resultados.size(); i++) {
                    for (int j = 0; j < resultados.size() - 1 - i; j++) {
                        if (resultados.get(j).frecuencia < resultados.get(j + 1).frecuencia) {
                            Contacto temp = resultados.get(j);
                            resultados.set(j, resultados.get(j + 1));
                            resultados.set(j + 1, temp);
                        }
                    }
                }
                
                System.out.println("\nResultados encontrados:");
                for (Contacto c : resultados) {
                    System.out.println(c + " [Búsquedas: " + c.frecuencia + "]");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar contacto.");
        }
    }

    static void eliminarContacto() {
        try {
            System.out.print("Ingrese nombre del contacto a eliminar: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese apellido: ");
            String apellido = sc.nextLine();
            System.out.print("Ingrese apodo: ");
            String apodo = sc.nextLine();
            System.out.print("Teléfono móvil: ");
            String movil = sc.nextLine();
            System.out.print("Teléfono convencional: ");
            String convencional = sc.nextLine();
            System.out.print("Correo electrónico: ");
            String email = sc.nextLine();

            if (nombre.isEmpty() || apellido.isEmpty() || apodo.isEmpty()) {
                System.out.println("Los campos nombre, apellido y apodo no pueden estar vacíos.");
                return;
            }

            Contacto c = new Contacto(nombre, apellido, apodo, movil, convencional, email);
            agenda.eliminarContacto(c);
            System.out.println("Contacto eliminado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar contacto.");
        }
    }

    static void exportarContactos() {
        try {
            System.out.print("Ingrese nombre del archivo para exportar (ej: backup.txt): ");
            String ruta = sc.nextLine();
            if (ruta.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }
            String resultado = agenda.exportarContactosAArchivo(ruta);
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("Error al exportar contactos.");
        }
    }
}
