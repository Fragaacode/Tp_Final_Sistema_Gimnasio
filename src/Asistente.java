
public final class Asistente extends Persona {

    private eTurno eTurno;

    /**   CONSTRUCTORES   **/
    public Asistente(int id) {
        super(id);
    }

    public Asistente(String nombre, int edad, String dni, int id, eTurno eTurno) {
        super(nombre, edad, dni, id);
        this.eTurno = eTurno;
    }

    /**   GETTERS AND SETTERS   **/

    public eTurno getTurno() {
        return eTurno;
    }

    public void setTurno(eTurno eTurno) {
        this.eTurno = eTurno;
    }

    /**   METODOS   **/

    @Override
    public String toString() {
        return "Datos de Asistente{" +
                "turno en el que trabaja =" + eTurno +
                "} " + super.toString();
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
