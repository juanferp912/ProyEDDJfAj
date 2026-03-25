import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static Agenda agenda = new Agenda();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        
        while (true) {
            System.out.println("\n____AGENDA DE CONTACTOS___");
            System.out.println("1. Cargar contactos desde archivo");
            System.out.println("2. Insertar contacto");
            System.out.println("3. Buscar por prefijo");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Salir");
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
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    static void cargarArchivo() {
        System.out.print("Ingrese la ruta del archivo (ej: contactos.txt): ");
        String ruta = sc.nextLine();
        agenda.cargarContactosDesdeArchivo(ruta);
    }

    static void insertarContacto() {
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

        Contacto c = new Contacto(nombre, apellido, apodo, movil, convencional, email);
        agenda.insertarContacto(c);
        System.out.println("Contacto insertado.");
    }

    static void buscarContacto() {
        System.out.print("Ingrese prefijo a buscar: ");
        String prefijo = sc.nextLine();
        LinkedList<Contacto> resultados = agenda.buscar(prefijo);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron contactos.");
        } else {
            System.out.println("\nResultados:");
            for (Contacto c : resultados) {
                System.out.println(c);
            }
        }
    }

    static void eliminarContacto() {
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

        Contacto c = new Contacto(nombre, apellido, apodo, movil, convencional, email);
        agenda.eliminarContacto(c);
        System.out.println("Contacto eliminado.");
    }
}
