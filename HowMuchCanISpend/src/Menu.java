import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                    menu.poupulate(mes,dia);
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
        System.out.println("4) Mostrar tabelas");

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
                menu.savedia();
                dia += 1;
                if( dia>main.quantosdiastemomes(mes)){
                    mes += 1;
                    dia = 1;
                    if (mes>12){
                        mes = 1;
                    }
                }
                BufferedWriter escritor = new BufferedWriter(new FileWriter("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\" + current.getNome()+"\\mes"+mes+".txt",true));


                Quando novodia = new Quando(mes,dia);
                float disponivel = main.getUsers().get(current).quantopossogastarnodia(novodia,current,current.dinheirodisponivelpormes());
                novodia.setPossogastar(disponivel);
                escritor.write(dia + ":" + disponivel+ "," );
                escritor.flush();
                escritor.close();
                main.addQuando(current,novodia,0);
                menu.clearWindow();
                System.out.println("Bem vindo a um novo dia: " + dia +"/" + mes);
                menu.caminhos();
            }
            case(4)->{
                menu.clearWindow();
                main.makeTables(current);
                menu.caminhos();

            }

            default -> throw new Exception("Opcao desconhecida");
        }
    }

    public void poupulate(int mes,int dia) throws Exception { //vai popular até ao dia anterior
        BufferedWriter escritor = new BufferedWriter(new FileWriter("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\" + current.getNome()+"\\mes"+mes+".txt",true));


        List<String> linhas = lerFicheiro("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\" + current.getNome()
                +"\\mes" + mes+".txt");
        if(linhas.size()>0){
            int tamanho = linhas.size();
            String ultima = linhas.get(tamanho-1);
            String[] linhaPartida;
            System.out.println(ultima);
            linhaPartida = ultima.split(":", 2);
            String[] campos = linhaPartida[1].split(",");
            if(campos.length<=1){
                escritor.write( "0" + "\n");
            }
        }


        for(int i = 1; i<=dia;i++){
            Quando quando = new Quando(mes,i, current.dinheirodisponivelpormes());
            int finalI = i;
            if(main.getUsers().get(current).getGastoPorDia().keySet().stream().filter(e->e.getMes()==mes && e.getDia()== finalI).collect(Collectors.toCollection(ArrayList::new)).size()==0){
                float disponivel = main.getUsers().get(current).quantopossogastarnodia(quando,current,current.dinheirodisponivelpormes());
                quando.setPossogastar(disponivel);
                main.addQuando(current,quando,0);
                if(i!=dia){
                    escritor.write(i + ":" + disponivel+ "," + "0" + "\n");
                    escritor.flush();
                }
                else{
                    escritor.write(i + ":" + disponivel+ ",");
                    escritor.flush();
                }
            }
            }
        escritor.close();
    }

    public void savedia() throws Exception {
        BufferedWriter escritor = new BufferedWriter(new FileWriter("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\" + current.getNome()+"\\mes"+mes+".txt",true));
        escritor.write((int) main.getGastoNoDia(current,mes,dia) + "\n");
        escritor.flush();
        escritor.close();
    }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }
}
