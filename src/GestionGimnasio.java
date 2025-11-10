import java.util.*;


    public class GestionGimnasio {
        private ArrayList<Cliente> clientes;           // Lista ordenable (ArrayList)
        private HashSet<Asistente> asistentes;         // Evita duplicados
        private HashMap<String, Administrador> administradores; // Acceso rápido por usuario
        private TreeMap<String, Cliente> clientesOrdenadosPorNombre; // Orden natural por clave

        public GestionGimnasio() {
            this.clientes = new ArrayList<>();
            this.asistentes = new HashSet<>();
            this.administradores = new HashMap<>();
            this.clientesOrdenadosPorNombre = new TreeMap<>();
        }

        // =======================
        // ADMINISTRADORES
        // =======================
        public void agregarAdministrador(Administrador admin) {
            administradores.put(admin.getDni(), admin);
            System.out.println("Administrador agregado: " + admin.getNombre());
        }

        public void accesoAdministrador(String usuario, String pass)
                throws UsuarioNoAutorizadoException {
            boolean autorizado = false;

            for (Administrador admin : administradores.values()) {
                if (admin.iniciarSesion(usuario, pass)) {
                    autorizado = true;
                    System.out.println("✅ Bienvenido, " + admin.getNombre());
                    break;
                }
            }

            if (!autorizado)
                throw new UsuarioNoAutorizadoException("Acceso denegado: usuario o contraseña incorrectos.");
        }

        // =======================
        // CLIENTES
        // =======================
        public void agregarCliente(Cliente cliente) {
            clientes.add(cliente);
            clientesOrdenadosPorNombre.put(cliente.getNombre(), cliente);
            System.out.println("Cliente agregado: " + cliente.getNombre());
        }

        public void eliminarCliente(String dni) {
            clientes.removeIf(c -> c.getDni().equals(dni));
            clientesOrdenadosPorNombre.values().removeIf(c -> c.getDni().equals(dni));
            System.out.println("Cliente eliminado con DNI: " + dni);
        }

        public Cliente buscarClientePorDni(String dni) {
            for (Cliente c : clientes)
                if (c.getDni().equals(dni))
                    return c;
            return null;
        }

        public void pagarCuota(String dni, double monto) throws FondosInsuficientesException {
            Cliente c = buscarClientePorDni(dni);
            if (c == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }
            if (c.getSaldo() < monto)
                throw new FondosInsuficientesException("Fondos insuficientes para " + c.getNombre());

            c.setSaldo(c.getSaldo() - monto);
            System.out.println("Pago realizado. Nuevo saldo: $" + c.getSaldo());
        }

        public void mostrarClientes() {
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados.");
                return;
            }
            System.out.println("\n--- Lista de Clientes ---");
            for (Cliente c : clientes)
                System.out.println(c.getNombre() + " | DNI: " + c.getDni() +
                        " | Tipo cuota: " + c.getCuota() + " | Saldo: $" + c.getSaldo());
        }

        public void mostrarClientesOrdenadosPorNombre() {
            System.out.println("\n--- Clientes ordenados (TreeMap) ---");
            for (Map.Entry<String, Cliente> entry : clientesOrdenadosPorNombre.entrySet()) {
                Cliente c = entry.getValue();
                System.out.println(c.getNombre() + " | DNI: " + c.getDni());
            }
        }

        // =======================
        // ASISTENTES
        // =======================
        public void agregarAsistente(Asistente a) {
            if (asistentes.add(a))
                System.out.println("Asistente agregado: " + a.getNombre());
            else
                System.out.println("El asistente ya existe.");
        }

        public void mostrarAsistentes() {
            if (asistentes.isEmpty()) {
                System.out.println("No hay asistentes registrados.");
                return;
            }
            System.out.println("\n--- Lista de Asistentes ---");
            for (Asistente a : asistentes)
                System.out.println(a.getNombre() + " | DNI: " + a.getDni());
        }

        // =======================
        // ORDENAMIENTO
        // =======================
        public void ordenarClientesPorDni() {
            clientes.sort(Comparator.comparing(Cliente::getDni));
            System.out.println("Clientes ordenados por DNI.");
        }

        public void ordenarClientesPorNombre() {
            clientes.sort(Comparator.comparing(Cliente::getNombre));
            System.out.println("Clientes ordenados por nombre.");
        }
    }

