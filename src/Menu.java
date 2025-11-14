import java.util.Scanner;

public class Menu {
    private GestionGimnasio gestionGimnasio;

    public Menu() {
        this.gestionGimnasio = new GestionGimnasio();
    }


    public void iniciar()
    {
        agregadoDePersonas();
        GestionGimnasio g = new GestionGimnasio();
        Scanner sc = new Scanner(System.in);
        boolean salir=false;

        System.out.println("--Bienvenido al Sistema del Gimnasio--");


        while(!salir)
        {
            System.out.println("Ingrese su número de documento: ");
            String dni = sc.nextLine();
            Cliente busqueda = g.buscarClientePorDni(dni);
            Asistente buscado = g.buscarAsistentePorDni(dni);
            Administrador buscar=g.buscarAdministradorPorDni(dni);

            if(busqueda!=null){

            }

            System.out.println("4-Si desea salir del programa");
            int rol = sc.nextInt();
            switch (rol)
            {
                case 1:
                {
                    menuCliente();
                    break;
                }
                case 2:
                {
                    menuAsistente();
                    break;
                }
                case 3:
                {
                    menuAdministrador();
                    break;
                }
                case 4:
                {
                    salir=true;
                    break;
                }
                default:
                {
                    System.out.println("Comando invalido");
                    break;
                }
            }

        }
        sc.close();


    }
    public void menuCliente()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su DNI");
        String dni = sc.nextLine();
        Cliente c =gestionGimnasio.buscarClientePorDni(dni);
        System.out.println("Bienvenido"+c.getNombre()+", a continuacion indique que desea hacer");
        boolean salir=false;
        while(!salir)
        {
            System.out.println("1-Ver estado contable");
            System.out.println("2-Pagar cuota");
            System.out.println("3-Retroceder");
            int opcion = sc.nextInt();

            switch (opcion)
            {
                case 1:
                {
                    break;
                }
                case 2:
                {

                }
                case 3:
                {
                    salir=true;
                    break;
                }
                default:
                {
                    System.out.println("Comando invalido");
                    break;
                }
            }
        }
    }
    public void menuAsistente()
    {
        boolean salir=false;
        System.out.println("Indique que desea hacer");
        while(!salir)
        {
            System.out.println("1-");
        }
    }
    public void menuAdministrador()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su usuario");
        String user = sc.nextLine();
        System.out.println("Ingrese su contraseña");
        String contrasenia = sc.nextLine();
        boolean salir=false;
        System.out.println("Indique que desea hacer");
        while(!salir)
        {
            System.out.println("1-Agregar ");
        }
    }
    public void agregadoDePersonas()
    {
        GestionGimnasio gim = new GestionGimnasio();
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
    }
}
