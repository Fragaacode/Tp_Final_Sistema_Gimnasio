
public final class Cliente extends Persona implements iPagable {

    private Double saldo ;
    private eCuota eCuota;


    /**   CONSTRUCTORES   **/
    public Cliente(int id) {
        super(id);
    }

    public Cliente(String nombre, int edad, String dni, int id, Double saldo, eCuota eCuota) {
        super(nombre, edad, dni, id);
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

    public eCuota getCuota() {
        return eCuota;
    }

    public void setCuota(eCuota eCuota) {
        this.eCuota = eCuota;
    }


    /**   METODOS   **/
    @Override
    public void pagarCuota(double monto) throws FondosInsuficientesException {
        if (saldo<monto){
            throw new FondosInsuficientesException("Fondos Insuficientes para pagar la cuota");
        }
        saldo -= monto;
        System.out.println(getNombre() +"Ha pagado la cuota de $"+ monto + "Slado restante"+ saldo);
    }

    @Override
    public String toString() {
        return "Datos del Cliente{" +
                "saldo=" + saldo +
                ", cuota=" + eCuota +
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
