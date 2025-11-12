import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);


        Cliente c1 = new Cliente("Agustin",26,"223456",200.0,eCuota.ESTANDAR);
        Cliente c2 = new Cliente("Agusti",26,"223457",210.0,eCuota.ESTANDAR);
        Cliente c3 = new Cliente("Agust",26,"223458",220.0,eCuota.ESTANDAR);
        Cliente c4 = new Cliente("Agus",26,"223459",230.0,eCuota.ESTANDAR);
        Cliente c5 = new Cliente("Agu",26,"223450",240.0,eCuota.ESTANDAR);
        Cliente c6 = new Cliente("Agustinn",26,"223455",250.0,eCuota.ESTANDAR);

        Asistente a1 = new Asistente("Armando",33,"11123",eTurno.MANIANA);
        Asistente a2 = new Asistente("Armando",33,"11124",eTurno.TARDE);
        Asistente a3 = new Asistente("Armando",33,"11125",eTurno.NOCHE);
        Asistente a4 = new Asistente("Armando",33,"11126",eTurno.MANIANA);
        Asistente a5 = new Asistente("Armando",33,"11127",eTurno.MANIANA);

        Administrador admin =new Administrador("Admin", 22, "2345", "Addminn","admin22");



        GestionGimnasio gim = new GestionGimnasio();
        gim.agregarCliente(c1);
        gim.agregarCliente(c2);
        gim.agregarCliente(c3);
        gim.agregarCliente(c4);
        gim.agregarCliente(c5);
        gim.agregarCliente(c6);

        gim.agregarAsistente(a1);
        gim.agregarAsistente(a2);
        gim.agregarAsistente(a3);
        gim.agregarAsistente(a4);
        gim.agregarAsistente(a5);

        System.out.println("Ingrese su número de documento: ");
        String dni = scan.nextLine();

        Cliente busqueda = gim.buscarClientePorDni(dni);

        if (busqueda != null) {

            int opcion = 0;
            do {
                System.out.println("\n--- Menú de opciones ---");
                System.out.println("Opción 1 Consultar saldo actual");
                System.out.println("Opción 2 Pagar cuota ");
                System.out.println("Opción 3 Recargar saldo ");
                System.out.println("Opción 4 Mostrar datos: ");
                System.out.println("Opción 5 salir");

                System.out.print("Ingrese una opción: ");
                opcion = scan.nextInt();
                scan.nextLine();

                switch (opcion){
                    case 1 :

                        System.out.println("Su saldo actual es de: "+ busqueda.getSaldo());
                        break;

                    case 2 :
                        try {
                            busqueda.pagarCuota(250);
                        } catch (FondosInsuficientesException e) {
                            System.out.println(e.getMessage());
                        }

                        break;

                    case 3 :
                        System.out.println("Ingrese el saldo que desea recargar: ");
                        double recarga= scan.nextDouble();
                        scan.nextLine();
                        System.out.println(busqueda.recargarSaldo(recarga));

                        break;

                    case 4 :
                        System.out.println(busqueda.toString());

                        break;

                    case 5 :

                        System.out.println("Saliendo del menu de Clientes");

                        break;

                    default:
                        System.out.println("Opción inválida, intente nuevamente.");
                        break;
                }
            } while (opcion != 5);



            // parte del asistente
        } else {
            Asistente buscado = gim.buscarAsistentePorDni(dni);

            if (buscado != null) {

                int opcionn = 0;

                do {
                    System.out.println("\n--- Menú de opciones ---");
                    System.out.println("Opción 1 Ver datos Personales: ");
                    System.out.println("Opción 2 ver clientes Ordenados por nombre");
                    System.out.println("Opción 3 Ver clientes ordenados por Dni ");
                    System.out.println("Opción 4 Salir del menu ");

                    System.out.print("Ingrese una opción: ");
                    opcionn = scan.nextInt();
                    scan.nextLine();

                    switch (opcionn){
                        case 1 :
                            System.out.println(buscado.toString());
                            break;

                        case 2 :
                            gim.mostrarClientesOrdenadosPorNombre();
                            break;

                        case 3 :
                            gim.ordenarClientesPorDni();
                            gim.mostrarClientes();
                            break;

                        case 4 :
                            System.out.println("Saliendo del menu ");
                            break;

                        default:
                            System.out.println("Opción inválida, intente nuevamente.");
                            break;

                    }
                } while (opcionn != 4);


                //Parte del Admin
            }else {
                if (admin.getDni().equals(dni)) {
                    System.out.print("Ingrese su usuario: ");
                    String usuario = scan.nextLine();
                    System.out.print("Ingrese su contraseña: ");
                    String contraseña = scan.nextLine();

                    try {
                        if (admin.iniciarSesion(usuario, contraseña)) {
                            int opcion1 = 0;
                            System.out.println(" Acceso concedido. Bienvenido " + admin.getNombre());


                            do {
                                System.out.println(" Menu de Administrador: ");
                                System.out.println("Opcion 1 Mostrar datos del Admin");
                                System.out.println("Opcion 2 Mostrar Asistentes actuales");
                                System.out.println("Opcion 3 Eliminar Asitente");
                                System.out.println("Opcion 4 Crear nuevo Asistente");
                                System.out.println("Opcion 5 Crear nuevo Admin");
                                System.out.println("Opcion 6 Mostrar Admins actuales ");
                                System.out.println("Opcion 7 Salir");
                                opcion1 = scan.nextInt();
                                scan.nextLine();

                                switch (opcion1){
                                    case 1:
                                        System.out.println(admin.toString());

                                        break;
                                    case 2 :
                                        gim.mostrarAsistentes();
                                        break;
                                    case 3 :
                                        System.out.println("Ingrese el dni del Asistente que desea eliminar: ");
                                        gim.mostrarAsistentes();
                                        String dniE = scan.nextLine();
                                        gim.eliminarAsistente(dniE);
                                        break;
                                    case 4 :
                                        gim.crearNuevoAsistente();
                                        break;
                                    case 5 :
                                        gim.crearNuevoAdmin();

                                        break;
                                    case 6 :
                                        gim.mostrarAdministradores();
                                        break;
                                    case 7 :
                                        System.out.println("Saliendo");
                                        break;
                                    default:
                                        System.out.println("Opción inválida, intente nuevamente.");
                                        break;
                                }
                            }while (opcion1 != 7);

                        }
                    } catch (UsuarioNoAutorizadoException e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("No se encontro el dni ingresado ");
                }

            }

        }}}