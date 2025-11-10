public interface iPagable {
    void pagarCuota (double monto) throws  FondosInsuficientesException;
}