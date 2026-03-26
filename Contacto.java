public class Contacto {
    String nombre;
    String apellido;
    String apodo;
    String telefonoMovil;
    String telefonoConvencional;
    String correoElectronico;
    int frecuencia;

    public Contacto(String nombre, String apellido, String apodo, String telefonoMovil, 
                    String telefonoConvencional, String correoElectronico) {
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
