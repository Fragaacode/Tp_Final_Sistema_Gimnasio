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
            archivos.gimnasioAarchivo("gimnasio.json", gestionGimnasio);
        }
    }


    public void iniciar()
    {
        Scanner sc = new Scanner(System.in);
        boolean salir=false;




        while(!salir)
        {
            System.out.println("--Bienvenido al Sistema del Gimnasio--");
            System.out.println("Ingrese su número de documento para iniciar ");
            System.out.println("Ingrese 5 si desea cerrar el programa");
            String dni = sc.nextLine();
            if(dni.equals("5")){
                System.out.println("Saliendo del programa...");
                salir=true;
            }
            Cliente busqueda = gestionGimnasio.buscarClientePorDni(dni);
            Asistente buscado = gestionGimnasio.buscarAsistentePorDni(dni);
            Administrador buscar=gestionGimnasio.buscarAdministradorPorDni(dni);

            if(busqueda!=null)
            {
                menuCliente(busqueda,sc);
            }
            else if(buscado!=null)
            {
                menuAsistente(buscado,sc);
            }
            else if(buscar!=null)
            {
                menuAdministrador(buscar,sc);
            }
            else if(!dni.equals("5"))
            {
                System.out.println("El Dni ingresado no esta en nuestra base de datos");
            }




        }
        sc.close();


    }
    public void menuCliente(Cliente busqueda,Scanner sc)
    {
        System.out.println("Bienvenido/a"+busqueda.getNombre()+", a continuacion indique que desea hacer");
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
        System.out.println("Bienvenido/a"+buscado.getNombre()+", a continuacion indique que desea hacer");
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
                    System.out.println("Opcion 7 Salir");
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

    public void agregadoDePersonas()
    {

        ///HARDCODEO DE AGREGADO////

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

        leerYescribir.guardar("Clientes",archivos.serializarCliente(c1));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c2));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c3));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c4));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c5));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c6));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c7));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c8));
        leerYescribir.guardar("Clientes", archivos.serializarCliente(c9));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c10));
        leerYescribir.guardar("Clientes", archivos.serializarCliente(c11));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c12));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c13));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c14));
        leerYescribir.guardar("Clientes",archivos.serializarCliente(c15));


        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a1));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a2));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a3));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a4));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a5));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a6));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a7));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a8));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a9));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a10));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a11));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a12));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a13));
        leerYescribir.guardar("Asistentes", archivos.serializarAsistente(a14));
        leerYescribir.guardar("Asistentes",archivos.serializarAsistente(a15));

        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin));
        leerYescribir.guardar("Administradores", archivos.serializarAdministrador(admin1));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin2));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin3));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin4));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin5));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin6));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin6));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin8));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin9));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin10));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin11));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin12));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin13));
        leerYescribir.guardar("Administradores",archivos.serializarAdministrador(admin14));



    }
}
