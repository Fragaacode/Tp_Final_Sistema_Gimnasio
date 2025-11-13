
public final class Asistente extends Persona {

    private eTurno eTurno;

    /**   CONSTRUCTORES   **/
    public Asistente() {
        super();
    }

    public Asistente(String nombre, int edad, String dni, int id, eTurno eTurno) {
        super(nombre, edad, dni);
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

    public String toString() {
        String var10000 = String.valueOf(this.eTurno);
        return "Datos de Asistente{turno en el que trabaja =" + var10000 + "} " + super.toString();
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
