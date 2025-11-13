import java.util.Objects;

public abstract class Persona {
    private String nombre;
    private int edad;
    private String dni;
    private int id;
    private static int contador = 0;

    /**   CONSTRUCTORES   **/

    public Persona(String nombre, int edad, String dni) {
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
        this.id = contador++;
    }

    public Persona() {
    }


    /**   GETTERS Y SETTERS   **/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    /**   GETEER DE ID SIN SETTER   **/
    public int getId() {
        return id;
    }


    /**   METODOS   **/

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", dni='" + dni + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return id == persona.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
