public final class Administrador extends Persona {
    private String usuario;
    private String contraseña;

    /**   CONSTRUCTORES   **/
    public Administrador(int id) {
        super(id);
    }

    public Administrador(String nombre, int edad, String dni, int id, String usuario, String contraseña) {
        super(nombre, edad, dni, id);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    /**   GETTERS **/
    // LOS GETTERS LOS VAMOS A USAR PARA LA VALIDACION DE LAS CREDENCIALES DEL ADMIN
    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    /**   METODOS   **/
    public boolean iniciarSesion(String user, String pass) throws UsuarioNoAutorizadoException{
        if (usuario.equalsIgnoreCase(user) && contraseña.equalsIgnoreCase(pass)) {

            System.out.println("inicio de Sesion exitoso, Bienvenido: "+ getNombre());
        }
        else {
            System.out.println("usuario o contraseña ingresado incorrectamente");
        }
        return false;
    }
    @Override
    public String toString() {
        return "Datos del administrador :"+super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
