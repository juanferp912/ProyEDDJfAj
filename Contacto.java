public class Contacto {
    static int contador = 0;  //para evitar que se dupliquen al buscar por prefijo
    int id;
    String nombre;
    String apellido;
    String apodo;
    String telefonoMovil;
    String telefonoConvencional;
    String correoElectronico;
    int frecuencia;

    public Contacto(String nombre, String apellido, String apodo, String telefonoMovil, 
                    String telefonoConvencional, String correoElectronico) {
        this.id = contador++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.telefonoMovil = telefonoMovil;
        this.telefonoConvencional = telefonoConvencional;
        this.correoElectronico = correoElectronico;
        this.frecuencia = 0;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + apodo + ") - Móvil: " + telefonoMovil + 
               " | Conv: " + telefonoConvencional + " | Email: " + correoElectronico;
    }
}
