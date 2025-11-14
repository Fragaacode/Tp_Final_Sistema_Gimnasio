import java.util.Scanner;

public class Menu {
    private GestionGimnasio gestionGimnasio;

    public Menu() {
        this.gestionGimnasio = new GestionGimnasio();
    }


    public void iniciar()
    {
        agregadoDePersonas();
        Scanner sc = new Scanner(System.in);
        boolean salir=false;

        System.out.println("--Bienvenido al Sistema del Gimnasio--");


        while(!salir)
        {
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
                    break;
                }
                case 5:
                {
                    System.out.println("Ingrese el dni del cliente que desea dar de baja");
                    String dni=sc.nextLine();
                    Cliente baja=gestionGimnasio.buscarClientePorDni(dni);
                    if(baja!=null){
                        gestionGimnasio.darDeBaja(baja);
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
                    System.out.println(" Menu de Administrador: ");
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
        Cliente c1 = new Cliente("Agustin",26,"223456",200.0,eCuota.DIA);
        Cliente c2 = new Cliente("Agusti",26,"223457",210.0,eCuota.SEMANAL);
        Cliente c3 = new Cliente("Agust",26,"223458",220.0,eCuota.SEMANAL);
        Cliente c4 = new Cliente("Agus",26,"223459",230.0,eCuota.MENSUAL);
        Cliente c5 = new Cliente("Agu",26,"223450",240.0,eCuota.DIA);
        Cliente c6 = new Cliente("Agustinn",26,"223455",250.0,eCuota.MENSUAL);


        Asistente a1 = new Asistente("Armando",33,"11123",eTurno.MANIANA);
        Asistente a2 = new Asistente("Armando",33,"11124",eTurno.TARDE);
        Asistente a3 = new Asistente("Armando",33,"11125",eTurno.NOCHE);
        Asistente a4 = new Asistente("Armando",33,"11126",eTurno.MANIANA);
        Asistente a5 = new Asistente("Armando",33,"11127",eTurno.MANIANA);

        Administrador admin =new Administrador("Admin", 22, "2345", "Addminn","admin22");




        gestionGimnasio.agregarCliente(c1);
        gestionGimnasio.agregarCliente(c2);
        gestionGimnasio.agregarCliente(c3);
        gestionGimnasio.agregarCliente(c4);
        gestionGimnasio.agregarCliente(c5);
        gestionGimnasio.agregarCliente(c6);

        gestionGimnasio.agregarAsistente(a1);
        gestionGimnasio.agregarAsistente(a2);
        gestionGimnasio.agregarAsistente(a3);
        gestionGimnasio.agregarAsistente(a4);
        gestionGimnasio.agregarAsistente(a5);

        gestionGimnasio.agregarAdministrador(admin);
    }
}
