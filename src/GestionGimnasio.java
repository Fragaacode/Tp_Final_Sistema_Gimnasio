import java.io.PrintStream;
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

        public void accesoAdministrador(String usuario, String pass) throws UsuarioNoAutorizadoException {
            boolean autorizado = false;
            Iterator var4 = this.administradores.values().iterator();

            while(var4.hasNext()) {
                Administrador admin = (Administrador)var4.next();
                if (admin.iniciarSesion(usuario, pass)) {
                    autorizado = true;
                    System.out.println("✅ Bienvenido, " + admin.getNombre());
                    break;
                }
            }

            if (!autorizado) {
                throw new UsuarioNoAutorizadoException("Acceso denegado: usuario o contraseña incorrectos.");
            }
        }
        public void crearNuevoAdmin() {
            Scanner scan = new Scanner(System.in);
            System.out.print("Ingrese nombre del administrador: ");
            String nombre = scan.nextLine();
            System.out.print("Ingrese edad: ");
            int edad = scan.nextInt();
            scan.nextLine();
            System.out.print("Ingrese DNI: ");
            String dni = scan.nextLine();
            System.out.print("Ingrese nombre de usuario: ");
            String usuario = scan.nextLine();
            System.out.print("Ingrese contraseña: ");
            String contrasenia = scan.nextLine();
            Administrador nuevoAdmin = new Administrador(nombre,edad,dni,usuario,contrasenia);
            this.agregarAdministrador(nuevoAdmin);
            System.out.println("✅ Nuevo administrador creado exitosamente.");
        }

        public void mostrarAdministradores() {
            if (this.administradores.isEmpty()) {
                System.out.println("No hay administradores registrados.");
            } else {
                System.out.println("\n--- Lista de Administradores ---");
                Iterator var1 = this.administradores.values().iterator();

                while(var1.hasNext()) {
                    Administrador admin = (Administrador)var1.next();
                    PrintStream var10000 = System.out;
                    String var10001 = admin.getNombre();
                    var10000.println("Nombre: " + var10001 + " DNI: " + admin.getDni() + " Usuario: " + admin.getUsuario());
                }
            }

        }
        public Administrador buscarAdministradorPorDni(String dni)
        {
            Iterator var2 = this.administradores.values().iterator();

            Administrador a;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                a = (Administrador)var2.next();
            } while(!a.getDni().equals(dni));

            return a;
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
            Iterator var2 = this.clientes.iterator();

            Cliente c;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                c = (Cliente)var2.next();
            } while(!c.getDni().equals(dni));

            return c;
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
            if (this.clientes.isEmpty()) {
                System.out.println("No hay clientes registrados.");
            } else {
                System.out.println("\n--- Lista de Clientes ---");
                Iterator var1 = this.clientes.iterator();

                while(var1.hasNext()) {
                    Cliente c = (Cliente)var1.next();
                    PrintStream var10000 = System.out;
                    String var10001 = c.getNombre();
                    var10000.println(var10001 + " | DNI: " + c.getDni() + " | Tipo cuota: " + String.valueOf(c.geteCuota()) + " | Saldo: $" + c.getSaldo());
                }
            }

        }

        public void mostrarClientesOrdenadosPorNombre() {
            System.out.println("\n--- Clientes ordenados (TreeMap) ---");
            Iterator var1 = this.clientesOrdenadosPorNombre.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry<String, Cliente> entry = (Map.Entry)var1.next();
                Cliente c = (Cliente)entry.getValue();
                PrintStream var10000 = System.out;
                String var10001 = c.getNombre();
                var10000.println(var10001 + " | DNI: " + c.getDni());
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
            if (this.asistentes.isEmpty()) {
                System.out.println("No hay asistentes registrados.");
            } else {
                System.out.println("\n--- Lista de Asistentes ---");
                Iterator var1 = this.asistentes.iterator();

                while(var1.hasNext()) {
                    Asistente a = (Asistente)var1.next();
                    PrintStream var10000 = System.out;
                    String var10001 = a.getNombre();
                    var10000.println(var10001 + " | DNI: " + a.getDni());
                }
            }

        }
        public Asistente buscarAsistentePorDni(String dni) {
            Iterator var2 = this.asistentes.iterator();

            Asistente a;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                a = (Asistente)var2.next();
            } while(!a.getDni().equals(dni));

            return a;
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

        public ArrayList<Cliente> getClientes() {
            return clientes;
        }

        public void setClientes(ArrayList<Cliente> clientes) {
            this.clientes = clientes;
        }

        public HashSet<Asistente> getAsistentes() {
            return asistentes;
        }

        public void setAsistentes(HashSet<Asistente> asistentes) {
            this.asistentes = asistentes;
        }

        public HashMap<String, Administrador> getAdministradores() {
            return administradores;
        }

        public void setAdministradores(HashMap<String, Administrador> administradores) {
            this.administradores = administradores;
        }

        public TreeMap<String, Cliente> getClientesOrdenadosPorNombre() {
            return clientesOrdenadosPorNombre;
        }

        public void setClientesOrdenadosPorNombre(TreeMap<String, Cliente> clientesOrdenadosPorNombre) {
            this.clientesOrdenadosPorNombre = clientesOrdenadosPorNombre;
        }
    }

