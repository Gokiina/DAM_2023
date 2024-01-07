package Florida.AE2;

/**
 * La clase Usuario almacena el nombre de usuario y la contraseña de un usuario.
 */
public class Usuario {
    private String nombre;
    private String contraseña;

    /**
     * Constructor de la clase usuario
     * @param nombre Nombre del usuario
     * @param contraseña Contraseña del usuario
     */
    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    /**
     * Método para obtener el nombre del usuario
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para obtener la contraseña del usuario
     * @return Contraseña del usuario
     */
    public String getContraseña() {
        return contraseña;
    }
}