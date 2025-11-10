public class UsuarioNoAutorizado extends RuntimeException {
  public UsuarioNoAutorizado(String message) {
    super(message);
  }
}
