import java.util.Objects;

public class mantenimiento extends Persona{
    private String sectorAsignado;

    public mantenimiento(){

    }

    public mantenimiento(String nombre, int edad, String dni, String sectorAsignado) {
        super(nombre, edad, dni);
        this.sectorAsignado = sectorAsignado;
    }

    public String getSectorAsignado() {
        return sectorAsignado;
    }

    public void setSectorAsignado(String sectorAsignado) {
        this.sectorAsignado = sectorAsignado;
    }
    public String toString() {
        String var10000 = String.valueOf(this.sectorAsignado);
        return "Datos de la perosna de mantenimiento{sector en el que trabaja =" + var10000 + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        mantenimiento that = (mantenimiento) o;
        return Objects.equals(sectorAsignado, that.sectorAsignado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sectorAsignado);
    }
}
