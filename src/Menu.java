import java.util.Scanner;

public class Menu {
    private GestionGimnasio gestionGimnasio;

    public Menu() {
        this.gestionGimnasio = new GestionGimnasio();
    }


    public void iniciar()
    {
        Scanner sc = new Scanner(System.in);
        boolean salir=false;

        System.out.println("--Bienvenido al Sistema del Gimansio--");

        while(!salir)
        {
            System.out.println("Indique su tipo de cuenta");
            System.out.println("1-Cliente");
            System.out.println("2-Asistente");
            System.out.println("3-Administrador");
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
}
