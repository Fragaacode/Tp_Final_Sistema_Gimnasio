import java.util.Objects;

public class Profesor extends Persona{
    private String especialidad;

    public Profesor(){

    }

    public Profesor(String nombre, int edad, String dni, String especialidad) {
        super(nombre, edad, dni);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }


    public String toString() {
        String var10000 = String.valueOf(this.especialidad);
        return "Datos del Profesor{especialidad =" + var10000 + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Profesor profesor = (Profesor) o;
        return Objects.equals(especialidad, profesor.especialidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), especialidad);
    }
}
