import java.util.Scanner;

public class Menu {
    private GestionGimnasio gestionGimnasio;
    private GestorJSON archivos;
    private LecturaEscrituraDeArchivo leerYescribir;



    public Menu() {
        this.archivos = new GestorJSON();
        this.leerYescribir = new LecturaEscrituraDeArchivo();

        GestionGimnasio cargado = archivos.ArchivoAGimnasio("gimnasio.json");

        if(cargado != null) {
            System.out.println("Archivo cargado correctamente.");
            this.gestionGimnasio = cargado;
        } else {
            System.out.println("No existe archivo. Creando uno nuevo...");
            this.gestionGimnasio = new GestionGimnasio();
            agregadoDePersonas();
            archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
        }
    }


    public void iniciar()
    {
        Scanner sc = new Scanner(System.in);
        boolean salir=false;




        while(!salir) {
            System.out.println("--Bienvenido al Sistema del Gimnasio--");
            boolean existe= false;
            System.out.println("Presione 1 si esta registrado o Presione 2 si no lo esta: ");
            int opcion2 = sc.nextInt();
            sc.nextLine();

            switch (opcion2)
            {
                case 1 :

                    break;

                case 2 :
                    existe = true;
                    break;
                default:
                    System.out.println("Comando invalido, intente de nuevo");
                    break;

            }

            if (existe == false) {
                System.out.println("Ingrese su número de documento para iniciar ");
                System.out.println("Ingrese 5 si desea cerrar el programa");
                String dni = sc.nextLine();
                if (dni.equals("5")) {
                    System.out.println("Saliendo del programa...");
                    salir = true;
                }
                Cliente busqueda = gestionGimnasio.buscarClientePorDni(dni);
                Asistente buscado = gestionGimnasio.buscarAsistentePorDni(dni);
                Administrador buscar = gestionGimnasio.buscarAdministradorPorDni(dni);
                Profesor busca = gestionGimnasio.buscarProfesorPorDni(dni);
                mantenimiento busc = gestionGimnasio.buscarmantenimientoPorDni(dni);

                if (busqueda != null) {
                    menuCliente(busqueda, sc);
                } else if (buscado != null) {
                    menuAsistente(buscado, sc);
                } else if (buscar != null) {
                    menuAdministrador(buscar, sc);
                } else if (busca != null) {
                    menuProfesor(busca, sc);
                } else if (busc != null) {
                    menuMantenimiento(busc, sc);
                } else if (!dni.equals("5")) {
                    System.out.println("El Dni ingresado no esta en nuestra base de datos");
                }


            }else{
                gestionGimnasio.crearNuevoCliente();
                archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
            }


        }
        sc.close();
    }
    public void menuCliente(Cliente busqueda,Scanner sc)
    {
        System.out.println("Bienvenido/a "+busqueda.getNombre()+", a continuacion indique que desea hacer");
        boolean salir=false;
        while(!salir)
        {
            System.out.println("--Menu para Clientes--");
            System.out.println("Opción 1 Consultar saldo actual");
            System.out.println("Opción 2 Pagar cuota ");
            System.out.println("Opción 3 Recargar saldo ");
            System.out.println("Opción 4 Mostrar datos: ");
            System.out.println("Opción 5 salir del menu");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion)
            {
                case 1:
                {
                    System.out.println("Su saldo actual es de: "+ busqueda.getSaldo());
                    break;
                }
                case 2:
                {
                    try {
                        gestionGimnasio.pagarCuota(busqueda);
                        archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    } catch (FondosInsuficientesException e) {
                        System.out.println(e.getMessage());

                    }
                    break;
                }
                case 3:
                {
                    System.out.println("Ingrese el saldo que desea recargar: ");
                    double recarga= sc.nextDouble();
                    sc.nextLine();
                    System.out.println(gestionGimnasio.recargarSaldo(busqueda,recarga));
                    archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);

                    break;
                }
                case 4:
                {
                    System.out.println(busqueda.toString());
                    break;
                }
                case 5:
                {
                    System.out.println("Saliendo del menu de Clientes...");
                    salir=true;
                    break;
                }
                default:
                {
                    System.out.println("Comando invalido, intente de nuevo");
                    break;
                }
            }
        }
    }
    public void menuAsistente(Asistente buscado,Scanner sc)
    {
        boolean salir=false;
        System.out.println("Bienvenido/a "+buscado.getNombre()+", a continuacion indique que desea hacer");
        while(!salir)
        {
            System.out.println("--Menu para Asistentes--");
            System.out.println("Opción 1 Ver datos Personales: ");
            System.out.println("Opción 2 ver clientes Ordenados por nombre");
            System.out.println("Opción 3 Ver clientes ordenados por Dni ");
            System.out.println("Opcion 4 crear cliente");
            System.out.println("Opcion 5 dar de baja cliente");
            System.out.println("Opcion 6 eliminar cliente");
            System.out.println("Opción 7 Salir del menu ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion)
            {
                case 1:
                {
                    System.out.println(buscado.toString());
                    break;
                }
                case 2:
                    {
                        gestionGimnasio.mostrarClientesOrdenadosPorNombre();
                        break;
                    }
                case 3:
                {
                    gestionGimnasio.ordenarClientesPorDni();
                    gestionGimnasio.mostrarClientes();
                    break;

                }
                case 4:
                {
                    gestionGimnasio.crearNuevoCliente();
                    archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    break;
                }
                case 5:
                {
                    System.out.println("Ingrese el dni del cliente que desea dar de baja");
                    String dni=sc.nextLine();
                    Cliente baja=gestionGimnasio.buscarClientePorDni(dni);
                    if(baja!=null){
                        gestionGimnasio.darDeBaja(baja);
                        archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    }
                    else
                    {
                        System.out.println("Ingreso un dni erroneo");
                    }
                    break;
                }
                case 6:
                {
                    System.out.println("Ingrese el dni del cliente que desea eliminar");
                    String sacar=sc.nextLine();
                    Cliente eliminar=gestionGimnasio.buscarClientePorDni(sacar);
                    if(eliminar!=null)
                    {
                        gestionGimnasio.eliminarCliente(eliminar.getDni());
                        archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    }
                    else
                    {
                        System.out.println("Ingreso un dni erroneo");
                    }

                    break;
                }
                case 7:
                {
                    System.out.println("Saliendo del menu de Asistente...");
                    salir=true;
                    break;
                }
                default:
                {
                    System.out.println("Comando invalido, intente de nuevo");
                    break;
                }
            }

        }
    }
    public void menuAdministrador(Administrador buscar,Scanner sc)
    {
        System.out.println("Ingrese su usuario");
        String usuario = sc.nextLine();
        System.out.println("Ingrese su contraseña");
        String contraseña = sc.nextLine();
        boolean salir=false;
       try {
            if (buscar.iniciarSesion(usuario,contraseña))
            {
                int opcion1 = 0;
                while(!salir)
                {
                    System.out.println("--Menu para Administradores--");
                    System.out.println("Opcion 1 Mostrar datos del Admin");
                    System.out.println("Opcion 2 Mostrar Asistentes actuales");
                    System.out.println("Opcion 3 Eliminar Asitente");
                    System.out.println("Opcion 4 Crear nuevo Asistente");
                    System.out.println("Opcion 5 Crear nuevo Admin");
                    System.out.println("Opcion 6 Mostrar Admins actuales ");
                    System.out.println("Opcion 7 Mostrar Profesores actuales");
                    System.out.println("Opcion 8 Eliminar Profesor");
                    System.out.println("Opcion 9 Crear nuevo Profesor");
                    System.out.println("Opcion 10 Mostrar Trabajadores de Mantenimiento actuales");
                    System.out.println("Opcion 11 Eliminar Trabajador de Mantenimiento");
                    System.out.println("Opcion 12 Crear nuevo Trabajafor de Mantenimiento");
                    System.out.println("Opcion 13 Salir");
                    opcion1 = sc.nextInt();
                    sc.nextLine();

                    switch (opcion1){
                        case 1:
                        {
                            System.out.println(buscar.toString());
                            break;
                        }
                        case 2 :
                        {
                            gestionGimnasio.mostrarAsistentes();
                            break;
                        }
                        case 3 :
                        {
                            System.out.println("Ingrese el dni del Asistente que desea eliminar: ");
                            gestionGimnasio.mostrarAsistentes();
                            String dniE = sc.nextLine();
                            gestionGimnasio.eliminarAsistente(dniE);
                            break;
                        }
                        case 4 :
                        {
                            gestionGimnasio.crearNuevoAsistente();
                            break;
                        }
                        case 5 :
                        {
                            gestionGimnasio.crearNuevoAdmin();
                            break;
                        }
                        case 6 :
                        {
                            gestionGimnasio.mostrarAdministradores();
                            break;
                        }
                        case 7 :
                        {
                            gestionGimnasio.mostrarProfesores();
                            break;
                        }
                        case 8 :
                        {
                            System.out.println("Ingrese el dni del Profesor que desea eliminar: ");
                            gestionGimnasio.mostrarProfesores();
                            String dniP = sc.nextLine();
                            gestionGimnasio.eliminarProfesor(dniP);
                            break;
                        }
                        case 9 :
                        {
                            gestionGimnasio.crearNuevoProfesor();
                            break;
                        }
                        case 10 :
                        {
                            gestionGimnasio.mostrarMantenimiento();
                            break;
                        }
                        case 11 :
                        {
                            System.out.println("Ingrese el dni del Asistente que desea eliminar: ");
                            gestionGimnasio.mostrarMantenimiento();
                            String dniM = sc.nextLine();
                            gestionGimnasio.eliminarMantenimiento(dniM);
                            break;
                        }
                        case 12 :
                        {
                            gestionGimnasio.crearNuevoMantenimeinto();
                            break;
                        }
                        case 13 :
                        {
                            System.out.println("Saliendo del menu de Administrador...");
                            salir=true;
                            break;
                        }
                        default:
                        {
                            System.out.println("Comando invalido, intente de nuevo");
                            break;
                        }
                    }
                }
            }
        }
        catch (UsuarioNoAutorizadoException e) {
        System.out.println(e.getMessage());
       }
    }

    public void menuProfesor(Profesor buscado, Scanner sc)
    {
    boolean salir = false;
        System.out.println("Bienvenido/a "+buscado.getNombre()+" , a continuacion indique que desea hacer: ");
        while (!salir)
        {
            System.out.println("--Menu para Profesores--");
            System.out.println("Opcion 1 Ver datos Personales: ");
            System.out.println("Opción 2 ver clientes Ordenados por nombre");
            System.out.println("Opción 3 Ver clientes ordenados por Dni ");
            System.out.println("Opcion 4 crear cliente");
            System.out.println("Opcion 5 dar de baja cliente");
            System.out.println("Opcion 6 eliminar cliente");
            System.out.println("Opción 7 Salir del menu ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion)
            {
                case 1:
                {
                    System.out.println(buscado.toString());
                    break;
                }
                case 2:
                {
                    gestionGimnasio.mostrarClientesOrdenadosPorNombre();
                    break;
                }
                case 3:
                {
                    gestionGimnasio.ordenarClientesPorDni();
                    gestionGimnasio.mostrarClientes();
                    break;

                }
                case 4:
                {
                    gestionGimnasio.crearNuevoCliente();
                    archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    break;
                }
                case 5:
                {
                    System.out.println("Ingrese el dni del cliente que desea dar de baja");
                    String dni=sc.nextLine();
                    Cliente baja=gestionGimnasio.buscarClientePorDni(dni);
                    if(baja!=null){
                        gestionGimnasio.darDeBaja(baja);
                        archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    }
                    else
                    {
                        System.out.println("Ingreso un dni erroneo");
                    }
                    break;
                }
                case 6:
                {
                    System.out.println("Ingrese el dni del cliente que desea eliminar");
                    String sacar=sc.nextLine();
                    Cliente eliminar=gestionGimnasio.buscarClientePorDni(sacar);
                    if(eliminar!=null)
                    {
                        gestionGimnasio.eliminarCliente(eliminar.getDni());
                        archivos.sobreescribirGimnasioAarchivo("gimnasio.json", gestionGimnasio);
                    }
                    else
                    {
                        System.out.println("Ingreso un dni erroneo");
                    }

                    break;
                }
                case 7:
                {
                    System.out.println("Saliendo del menu de Profesor...");
                    salir=true;
                    break;
                }
                default:
                {
                    System.out.println("Comando invalido, intente de nuevo");
                    break;
                }
            }
        }
    }

    public void menuMantenimiento(mantenimiento buscado,Scanner sc)
    {
        boolean salir = false;
        System.out.println("Bienvenido/a "+buscado.getNombre()+" , a continuacion indique que desea hacer: ");
        while (!salir)
        {
            System.out.println("--Menu personal de Mantenimiento--");
            System.out.println("Opcion 1 Ver datos Personales: ");
            System.out.println("Opción 2 Salir del menu ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion)
            {
                case 1 :
                {
                    System.out.println(buscado.toString());
                }
                case 2:
                {
                    System.out.println("Saliendo del menu de Mantenimiento...");
                    salir=true;
                    break;
                }
                default:
                {
                    System.out.println("Comando invalido, intente de nuevo");
                    break;
                }
            }
        }
    }
    public void agregadoDePersonas()
    {

        ///HARDCODEO DE AGREGADO////
/*
        Cliente c1 = new Cliente("Agustin",18,"223456",200.0,eCuota.DIA);
        Cliente c2 = new Cliente("Franco",38,"223457",210.0,eCuota.SEMANAL);
        Cliente c3 = new Cliente("Marcos",20,"223458",220.0,eCuota.SEMANAL);
        Cliente c4 = new Cliente("Juan",28,"223459",230.0,eCuota.MENSUAL);
        Cliente c5 = new Cliente("Sofia",30,"223450",240.0,eCuota.DIA);
        Cliente c6 = new Cliente("Facundo",23,"223455",250.0,eCuota.MENSUAL);
        Cliente c7 = new Cliente("Juan Pérez", 25, "40123456", 15000.0, eCuota.MENSUAL);
        Cliente c8 = new Cliente("María López", 32, "38987654", 22000.0, eCuota.SEMANAL);
        Cliente c9 = new Cliente("Carlos Gómez", 28, "42111222", 8000.0, eCuota.SEMANAL);
        Cliente c10 = new Cliente("Lucía Fernández", 30, "40999888", 12000.0, eCuota.MENSUAL);
        Cliente c11 = new Cliente("Matías Rojas", 22, "43123412", 5000.0, eCuota.DIA);
        Cliente c12 = new Cliente("Agustina Romero", 27, "37123456", 18000.0, eCuota.DIA);
        Cliente c13 = new Cliente("Tomás Benítez", 35, "35991234", 9500.0, eCuota.MENSUAL);
        Cliente c14 = new Cliente("Valentina Suárez", 26, "42127890", 7000.0, eCuota.SEMANAL);
        Cliente c15 = new Cliente("Santiago Castro", 29, "40112233", 16000.0, eCuota.MENSUAL);
        Cliente c16 = new Cliente("Florencia Díaz", 24, "39123455", 6000.0, eCuota.DIA);

        Asistente a1 = new Asistente("Armando",40,"11123",eTurno.MANIANA);
        Asistente a2 = new Asistente("Juliana",36,"11124",eTurno.TARDE);
        Asistente a3 = new Asistente("Miranda",27,"11125",eTurno.NOCHE);
        Asistente a4 = new Asistente("Pedro",25,"11126",eTurno.MANIANA);
        Asistente a5 = new Asistente("Sebastian",19,"11127",eTurno.MANIANA);
        Asistente a6 = new Asistente("Julián Torres", 29, "33123456", eTurno.MANIANA);
        Asistente a7 = new Asistente("Brenda Acosta", 34, "34987654", eTurno.TARDE);
        Asistente a8 = new Asistente("Nicolás Herrera", 26, "38111222", eTurno.NOCHE);
        Asistente a9 = new Asistente("Pamela Ruiz", 31, "36999888", eTurno.TARDE);
        Asistente a10 = new Asistente("Hernán López", 28, "35123412", eTurno.MANIANA);
        Asistente a11= new Asistente("Marina Duarte", 25, "37123456", eTurno.NOCHE);
        Asistente a12 = new Asistente("Diego Molina", 33, "35991234", eTurno.MANIANA);
        Asistente a13 = new Asistente("Carolina Sosa", 27, "38127890", eTurno.TARDE);
        Asistente a14 = new Asistente("Pablo Ibáñez", 30, "36112233", eTurno.NOCHE);
        Asistente a15 = new Asistente("Rocío Medina", 24, "35123455", eTurno.MANIANA);

        Administrador admin =new Administrador("Mateo", 22, "44784019", "Addminn","admin22");
        Administrador admin1 = new Administrador("Juan Pérez", 30, "12345678", "juanp", "pass123");
        Administrador admin2 = new Administrador("María Gómez", 28, "87654321", "mariag", "abc456");
        Administrador admin3 = new Administrador("Carlos Díaz", 35, "11223344", "carlosd", "qwerty");
        Administrador admin4 = new Administrador("Laura Fernández", 26, "44332211", "lauraf", "xyz789");
        Administrador admin5 = new Administrador("Pedro Martínez", 40, "55667788", "pedrom", "admin123");
        Administrador admin6 = new Administrador("Sofía López", 32, "88776655", "sofial", "123abc");
        Administrador admin7 = new Administrador("Andrés Torres", 29, "99887766", "andrest", "pass456");
        Administrador admin8 = new Administrador("Valentina Rojas", 31, "66554433", "valentar", "xyz123");
        Administrador admin9 = new Administrador("Miguel Sánchez", 38, "33445566", "miguels", "admin456");
        Administrador admin10 = new Administrador("Camila Ortiz", 27, "77665544", "camilao", "pass789");
        Administrador admin11 = new Administrador("Fernando Castro", 36, "22113344", "fernandoc", "abc123");
        Administrador admin12 = new Administrador("Isabella Herrera", 25, "33442211", "isabellah", "qwe789");
        Administrador admin13 = new Administrador("Diego Ramírez", 33, "55664433", "diegor", "123xyz");
        Administrador admin14 = new Administrador("Natalia Vargas", 30, "77889900", "nataliav", "pass321");

        Profesor profesor1  = new Profesor("Freddy Fazbear", 25, "65345698", "Calistenia");
        Profesor profesor2  = new Profesor("María González", 32, "87658767", "Boxeo");
        Profesor profesor3  = new Profesor("Juan Pérez", 41, "76547658", "Yoga");
        Profesor profesor4  = new Profesor("Lucía Fernández", 28, "3564556", "Pilates");
        Profesor profesor5  = new Profesor("Carlos Ramírez", 37, "34534654", "CrossFit");
        Profesor profesor6  = new Profesor("Sofía Méndez", 30, "8765786", "Entrenamiento Funcional");
        Profesor profesor7  = new Profesor("Diego Herrera", 45, "54657564", "Musculación");
        Profesor profesor8  = new Profesor("Valentina López", 26, "34565467", "Calistenia");
        Profesor profesor9  = new Profesor("Ricardo Suárez", 39, "98768987", "Artes Marciales");
        Profesor profesor10 = new Profesor("Camila Ortega", 33, "56765765", "Boxeo");
        Profesor profesor11 = new Profesor("Martín Delgado", 29, "23523454", "Artes Marciales");
        Profesor profesor12 = new Profesor("Florencia Vega", 36, "12343254", "Aerobic");
        Profesor profesor13 = new Profesor("Esteban Soto", 42, "777888999", "Kickboxing");
        Profesor profesor14 = new Profesor("Daniela Navarro", 27, "111000222", "Crossfit");
        Profesor profesor15 = new Profesor("Tomás Quiroga", 34, "333222111", "Yoga");

        mantenimiento m1  = new mantenimiento("Luis Romero", 45, "333000111", "Baños");
        mantenimiento m2  = new mantenimiento("Jorge Martínez", 50, "22556677", "Vestuario");
        mantenimiento m3  = new mantenimiento("Natalia Prado", 29, "23667788", "Gimnasio");
        mantenimiento m4  = new mantenimiento("Hernán Barrios", 41, "24778899", "Pasillos");
        mantenimiento m5  = new mantenimiento("Elena Suárez", 33, "25889900", "Oficinas");
        mantenimiento m6  = new mantenimiento("Oscar Delgado", 47, "26990011", "Duchas");
        mantenimiento m7  = new mantenimiento("Micaela Torres", 31, "27101122", "Depósitos");
        mantenimiento m8  = new mantenimiento("Ramiro Silva", 44, "28212233", "Sala de Máquinas");
        mantenimiento m9 = new mantenimiento("Gabriela Rojas", 27, "29323344", "Recepción");
        mantenimiento m10 = new mantenimiento("Alejandro Molina", 52, "34878899", "Estacionamiento");


        gestionGimnasio.agregarCliente(c1);
        gestionGimnasio.agregarCliente(c2);
        gestionGimnasio.agregarCliente(c3);
        gestionGimnasio.agregarCliente(c4);
        gestionGimnasio.agregarCliente(c5);
        gestionGimnasio.agregarCliente(c6);
        gestionGimnasio.agregarCliente(c7);
        gestionGimnasio.agregarCliente(c8);
        gestionGimnasio.agregarCliente(c9);
        gestionGimnasio.agregarCliente(c10);
        gestionGimnasio.agregarCliente(c11);
        gestionGimnasio.agregarCliente(c12);
        gestionGimnasio.agregarCliente(c13);
        gestionGimnasio.agregarCliente(c14);
        gestionGimnasio.agregarCliente(c15);
        gestionGimnasio.agregarCliente(c16);

        gestionGimnasio.agregarAsistente(a1);
        gestionGimnasio.agregarAsistente(a2);
        gestionGimnasio.agregarAsistente(a3);
        gestionGimnasio.agregarAsistente(a4);
        gestionGimnasio.agregarAsistente(a5);
        gestionGimnasio.agregarAsistente(a6);
        gestionGimnasio.agregarAsistente(a7);
        gestionGimnasio.agregarAsistente(a8);
        gestionGimnasio.agregarAsistente(a9);
        gestionGimnasio.agregarAsistente(a10);
        gestionGimnasio.agregarAsistente(a11);
        gestionGimnasio.agregarAsistente(a12);
        gestionGimnasio.agregarAsistente(a13);
        gestionGimnasio.agregarAsistente(a14);
        gestionGimnasio.agregarAsistente(a15);

        gestionGimnasio.agregarAdministrador(admin);
        gestionGimnasio.agregarAdministrador(admin1);
        gestionGimnasio.agregarAdministrador(admin2);
        gestionGimnasio.agregarAdministrador(admin3);
        gestionGimnasio.agregarAdministrador(admin4);
        gestionGimnasio.agregarAdministrador(admin5);
        gestionGimnasio.agregarAdministrador(admin6);
        gestionGimnasio.agregarAdministrador(admin7);
        gestionGimnasio.agregarAdministrador(admin8);
        gestionGimnasio.agregarAdministrador(admin9);
        gestionGimnasio.agregarAdministrador(admin10);
        gestionGimnasio.agregarAdministrador(admin11);
        gestionGimnasio.agregarAdministrador(admin12);
        gestionGimnasio.agregarAdministrador(admin13);
        gestionGimnasio.agregarAdministrador(admin14);

        gestionGimnasio.agregarProfesor(profesor1);
        gestionGimnasio.agregarProfesor(profesor2);
        gestionGimnasio.agregarProfesor(profesor3);
        gestionGimnasio.agregarProfesor(profesor4);
        gestionGimnasio.agregarProfesor(profesor5);
        gestionGimnasio.agregarProfesor(profesor6);
        gestionGimnasio.agregarProfesor(profesor7);
        gestionGimnasio.agregarProfesor(profesor8);
        gestionGimnasio.agregarProfesor(profesor9);
        gestionGimnasio.agregarProfesor(profesor10);
        gestionGimnasio.agregarProfesor(profesor11);
        gestionGimnasio.agregarProfesor(profesor12);
        gestionGimnasio.agregarProfesor(profesor13);
        gestionGimnasio.agregarProfesor(profesor14);
        gestionGimnasio.agregarProfesor(profesor15);

        gestionGimnasio.agregarMantenimiento(m1);
        gestionGimnasio.agregarMantenimiento(m2);
        gestionGimnasio.agregarMantenimiento(m3);
        gestionGimnasio.agregarMantenimiento(m4);
        gestionGimnasio.agregarMantenimiento(m5);
        gestionGimnasio.agregarMantenimiento(m6);
        gestionGimnasio.agregarMantenimiento(m7);
        gestionGimnasio.agregarMantenimiento(m8);
        gestionGimnasio.agregarMantenimiento(m9);
        gestionGimnasio.agregarMantenimiento(m10);

 */
    }
}
