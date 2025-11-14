
public final class Cliente extends Persona implements iPagable {

    private Double saldo ;
    private eCuota eCuota;
    private boolean activo=true;


    /**   CONSTRUCTORES   **/
    public Cliente() {

    }

    public Cliente(String nombre, int edad, String dni, Double saldo, eCuota eCuota) {
        super(nombre, edad, dni);
        this.saldo = saldo;
        this.eCuota = eCuota;
        this.activo = true;
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
    public boolean isActivo() {
        return this.activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }




    /**   METODOS   **/
    @Override
    public void pagarCuota() throws FondosInsuficientesException {
        if (saldo<eCuota.getPrecio()){
            throw new FondosInsuficientesException("Fondos Insuficientes para pagar la cuota");
        }
        saldo -= eCuota.getPrecio();
        System.out.println(getNombre() +"Ha pagado la cuota de $"+ eCuota.getPrecio() + "Saldo restante"+ saldo);
    }
    public void recargarSaldo(double nuevoSaldo) {
        this.saldo = this.saldo + nuevoSaldo;
    }



    @Override
    public String toString() {
        String var10000 = super.toString();
        return "Datos del Cliente: \n" + var10000 + ", saldo=" + this.saldo + ", cuota="+this.eCuota.getPrecio()+", frecuencia= " + String.valueOf(this.eCuota)+", estado activo= "+isActivo();
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
