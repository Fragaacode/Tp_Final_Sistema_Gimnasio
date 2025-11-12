
public final class Cliente extends Persona implements iPagable {

    private Double saldo ;
    private eCuota eCuota;


    /**   CONSTRUCTORES   **/
    public Cliente () {

    }

    public Cliente(String nombre, int edad, String dni, Double saldo, eCuota eCuota) {
        super(nombre, edad, dni);
        this.saldo = saldo;
        this.eCuota = eCuota;
    }

    /**   GETTERS AND SETTERS   **/
    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public eCuota geteCuota() {
        return eCuota;
    }

    public void seteCuota(eCuota eCuota) {
        this.eCuota = eCuota;
    }

    /**   METODOS   **/
    @Override
    public void pagarCuota(double monto) throws FondosInsuficientesException {
        if (saldo<monto){
            throw new FondosInsuficientesException("Fondos Insuficientes para pagar la cuota");
        }
        saldo -= monto;
        System.out.println(getNombre() +" pagaste la cuota de $"+ monto + " salado restante $"+ saldo);
    }
    public String recargarSaldo(double nuevoSaldo){
        saldo += nuevoSaldo;
        return "Saldo actualizado";
    }

    @Override
    public String toString() {
        return "Datos del Cliente: \n"+ super.toString() +
                ", saldo=" + saldo +
                ", cuota=" + eCuota ;
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
