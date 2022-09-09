import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static  Main main = new Main();
    static  Menu menu = new Menu();
    static User current = new User();
    static int dia;
    static int mes;

    public static void main(String[] args) throws Exception {
        int opcao = 0;
        Parser.parseUsers(main);
        menu.opcoes();
        Scanner scan = new Scanner(System.in);
        opcao = scan.nextInt();

        switch (opcao) {
            case (1) -> {
                menu.clearWindow();
                if (menu.login()) {
                    System.out.println("Login feito");
                    menu.clearWindow();
                    System.out.println("Dia: ");
                    dia = scan.nextInt();
                    System.out.println("Mes: ");
                    mes = scan.nextInt();
                    menu.caminhos();

                } else {
                    menu.login();
                }
            }
            case (2) -> {
                menu.clearWindow();
                menu.registar();
                menu.opcoes();
            }
            case (0) -> System.exit(1);
            default -> throw new Exception("Opcao desconhecida");
        }
    }

    public void opcoes(){
        System.out.println("1) Login");
        System.out.println("2) Registar");
        System.out.println("0) Sair");
    }

    public void clearWindow(){
        for(int i = 0; i<100;i++){
            System.out.println();
        }
    }

    public boolean login(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Nome de utilizador: ");
        String nome = scan.nextLine();
        System.out.println("Password: ");
        String pass = scan.nextLine();
        current = main.identifica(nome);
        return main.login(nome, pass);
    }

    public void registar() throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Nome de utilizador: ");
        String nome = scan.nextLine();
        System.out.println("Password: ");
        String pass = scan.nextLine();
        System.out.println("Salario: ");
        int salario = scan.nextInt();
        System.out.println("Poupar por mes: ");
        int poupar = scan.nextInt();
        main.registarUser(nome,pass,salario,poupar);
    }

    public void caminhos() throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("1) Consultar quanto posso gastar");
        System.out.println("2) Adicionar gasto");
        System.out.println("3) Acabar o dia");
        int op = scan.nextInt();

        switch (op){
            case(1) ->{
                HCIS j = main.getUsers().get(current);
                System.out.println("Posso gastar: " +  j.getPossogastar(dia,mes));
                menu.caminhos();
            }
            case(2)->{
                System.out.println("Quanto foi gasto? ");
                int quanto = scan.nextInt();
                main.addgasto(current,mes,dia,quanto);
                menu.caminhos();
            }
            case(3)->{
                main.save();
                menu.caminhos();
            }

            default -> throw new Exception("Opcao desconhecida");
        }
    }

}
