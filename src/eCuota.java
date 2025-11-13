public enum eCuota {
    DIA(10000),
    SEMANAL(25000),
    MENSUAL(50000);
    private final double precio;

    eCuota(double precio)
    {
        this.precio=precio;
    }
    public double getPrecio()
    {
        return precio;
    }
}
