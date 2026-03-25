public class Contacto {
    String nombre;
    String apellido;
    String apodo;

    public Contacto(String nombre, String apellido, String apodo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + apodo + ")";
    }
}
