import java.io.PrintStream;
import java.util.*;

public class GestionGimnasio {
    private ArrayList<Cliente> clientes;           // Lista ordenable (ArrayList)
    private HashSet<Asistente> asistentes;         // Evita duplicados
    private HashMap<String, Administrador> administradores; // Acceso rápido por usuario
    private TreeMap<String, Cliente> clientesOrdenadosPorNombre; // Orden natural por clave
    private HashSet<Profesor> profesores;
    private HashSet<mantenimiento> mantenimientos;

    public GestionGimnasio() {
        this.clientes = new ArrayList<>();
        this.asistentes = new HashSet<>();
        this.administradores = new HashMap<>();
        this.clientesOrdenadosPorNombre = new TreeMap<>();
        this.profesores = new HashSet<>();
        this.mantenimientos = new HashSet<>();
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
            throw new UsuarioNoAutorizadoException("Acceso denegado: usuario o contraseña incorrectos. ");
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
    public void crearNuevoCliente() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scan.nextLine();
        System.out.print("Ingrese edad: ");
        int edad = scan.nextInt();
        scan.nextLine();
        System.out.print("Ingrese DNI: ");
        String dni = scan.nextLine();
        System.out.print("Ingrese el saldo a depositar: ");
        double saldo = scan.nextDouble();
        scan.nextLine();
        System.out.println("Seleccione la frecuencia con la que vendra, esta va acorde a su cuota:");
        System.out.println("1. DIA");
        System.out.println("2. SEMANAL");
        System.out.println("3. MENSUAL");
        int cuotaOpcion = scan.nextInt();
        scan.nextLine();

        eCuota cuota;
        switch (cuotaOpcion) {
            case 1:
                cuota = eCuota.DIA;
                break;
            case 2:
                cuota = eCuota.SEMANAL;
                break;
            case 3:
                cuota = eCuota.MENSUAL;
                break;
            default:
                System.out.println("Opción inválida. Se asignará solo este dia por defecto.");
                cuota = eCuota.DIA;
        }
        Cliente nuevoCliente = new Cliente(nombre,edad,dni,saldo,cuota);
        this.agregarCliente(nuevoCliente);
        System.out.println("✅ Nuevo cliente creado exitosamente.");
    }

    public void darDeBaja(Cliente c)
    {
        c.setActivo(false);
    }

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

    public void pagarCuota(Cliente c) throws FondosInsuficientesException {
        c.pagarCuota();
    }
    public String recargarSaldo(Cliente c,double nuevoSaldo) {
        c.recargarSaldo(nuevoSaldo);
        return "Saldo actualizado";
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
    public void crearNuevoAsistente() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Ingrese nombre del asistente: ");
        String nombre = scan.nextLine();

        System.out.print("Ingrese edad: ");
        int edad = scan.nextInt();
        scan.nextLine(); // limpiar buffer

        System.out.print("Ingrese DNI: ");
        String dni = scan.nextLine();

        System.out.println("Seleccione el turno del asistente:");
        System.out.println("1. Mañana");
        System.out.println("2. Tarde");
        System.out.println("3. Noche");
        int turnoOpcion = scan.nextInt();
        scan.nextLine();

        eTurno turno;
        switch (turnoOpcion) {
            case 1:
                turno = eTurno.MANIANA;
                break;
            case 2:
                turno = eTurno.TARDE;
                break;
            case 3:
                turno = eTurno.NOCHE;
                break;
            default:
                System.out.println("Opción inválida. Se asignará turno mañana por defecto.");
                turno = eTurno.MANIANA;
        }

        Asistente nuevoAsistente = new Asistente(nombre, edad, dni, turno);
        agregarAsistente(nuevoAsistente);

        System.out.println("Nuevo asistente creado exitosamente.");
    }
    public void eliminarAsistente(String dni) {
        boolean eliminado = asistentes.removeIf(a -> a.getDni().equals(dni));

        if (eliminado) {
            System.out.println("Asistente eliminado con DNI: " + dni);
        } else {
            System.out.println("No se encontró ningún asistente con DNI: " + dni);
        }
    }
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
    // MANTENIMIENTO
    // =======================
    public void crearNuevoMantenimeinto() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Ingrese nombre de la persona de mantenimiento: ");
        String nombre = scan.nextLine();

        System.out.print("Ingrese edad: ");
        int edad = scan.nextInt();
        scan.nextLine(); // limpiar buffer

        System.out.print("Ingrese DNI: ");
        String dni = scan.nextLine();

        System.out.println("Ingrese el sector donde estara la persona de mantenimiento: ");
        String sector = scan.nextLine();

        mantenimiento nuevoMantenimiento = new mantenimiento(nombre,edad,dni,sector);
        this.agregarMantenimiento(nuevoMantenimiento);
    }
    public void agregarMantenimiento (mantenimiento m) {
        if (mantenimientos.add(m))
            System.out.println("Persona de Mantenimiento agregado: " + m.getNombre());
        else
            System.out.println("Esa persona de mantenimiento ya existe. ");
    }
    public void eliminarMantenimiento (String dni) {
        boolean eliminado = mantenimientos.removeIf(m -> m.getDni().equals(dni));

        if (eliminado) {
            System.out.println("Persona de Mantenimiento eliminada con DNI: " + dni);
        } else {
            System.out.println("No se encontro a esa Persona de mantenimiento con DNI: " + dni);
        }
    }
    public void mostrarMantenimiento() {
        if (this.mantenimientos.isEmpty()) {
            System.out.println("No hay Personal de Mantenimiento registrado.");
        } else {
            System.out.println("\n--- Lista de Personal de Mantenimiento ---");
            Iterator var1 = this.mantenimientos.iterator();

            while(var1.hasNext()) {
                mantenimiento m = (mantenimiento) var1.next();
                PrintStream var10000 = System.out;
                String var10001 = m.getNombre();
                var10000.println(var10001 + " | DNI: " + m.getDni());
            }
        }

    }
    public mantenimiento buscarmantenimientoPorDni(String dni) {
        Iterator var2 = this.mantenimientos.iterator();

        mantenimiento m;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            m = (mantenimiento) var2.next();
        } while(!m.getDni().equals(dni));

        return m;
    }
    // =======================
    // PROFESORES
    // =======================
    public void crearNuevoProfesor() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Ingrese nombre del profesor: ");
        String nombre = scan.nextLine();

        System.out.print("Ingrese edad: ");
        int edad = scan.nextInt();
        scan.nextLine(); // limpiar buffer

        System.out.print("Ingrese DNI: ");
        String dni = scan.nextLine();

        System.out.println("Ingrese la especialidad del Profesor: ");
        String especialidad = scan.nextLine();

        Profesor nuevoProfesor = new Profesor(nombre,edad,dni,especialidad);
        this.agregarProfesor(nuevoProfesor);
    }
    public void agregarProfesor (Profesor p) {
        if (profesores.add(p))
            System.out.println("Profesor agregado: " + p.getNombre());
        else
            System.out.println("Ese Profesor ya existe.");
    }
    public void eliminarProfesor (String dni) {
        boolean eliminado = profesores.removeIf(p -> p.getDni().equals(dni));

        if (eliminado) {
            System.out.println("Profesor eliminada con DNI: " + dni);
        } else {
            System.out.println("No se encontro al Profesor con DNI: " + dni);
        }
    }


    public void mostrarProfesores() {
        if (this.profesores.isEmpty()) {
            System.out.println("No hay Profesores registrados. ");
        } else {
            System.out.println("\n--- Lista de Profesores ---");
            Iterator var1 = this.profesores.iterator();

            while(var1.hasNext()) {
                Profesor p = (Profesor)var1.next();
                PrintStream var10000 = System.out;
                String var10001 = p.getNombre();
                var10000.println(var10001 + " | DNI: " + p.getDni());
            }
        }

    }
    public Profesor buscarProfesorPorDni(String dni) {
        Iterator var2 = this.profesores.iterator();

        Profesor p;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            p = (Profesor) var2.next();
        } while(!p.getDni().equals(dni));

        return p;
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

    public HashSet<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(HashSet<Profesor> profesores) {
        this.profesores = profesores;
    }

    public HashSet<mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(HashSet<mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }
}

