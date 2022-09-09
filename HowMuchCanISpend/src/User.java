import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class User {
    private String nome;
    private String password;
    private int salario;
    private int poupar;
    private int id;

    public User(){
        this.nome = "";
        this.password = "";
        this.salario = 0;
        this.poupar = 0;
        this.id = 0;
    }

    public User(String nome, String password, int id) {
        this.nome = nome;
        this.password = password;
        this.id = id;
        this.salario = 0;
        this.poupar = 0;
    }

    public User(String nome, String password, int salario, int poupar, int id) {
        this.nome = nome;
        this.password = password;
        this.salario = salario;
        this.poupar = poupar;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public int getPoupar() {
        return poupar;
    }

    public void setPoupar(int poupar) {
        this.poupar = poupar;
    }

    public int dinheirodisponivelpormes(){
        return this.salario-this.poupar;
    }

    public static User parse(String input, String nome) {
        String[] campos = input.split(",");
        return new User(nome,campos[0],Integer.parseInt(campos[1]),Integer.parseInt(campos[2]),Integer.parseInt(campos[3]));
    }

    public void guarda() throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\logins"));
        escritor.write(this.nome + ":" + this.password + "," + this.salario+ "," + this.poupar+ "," + this.id+ "\n");
        Path path = Paths.get("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\"+this.nome);
        Files.createDirectory(path);
        escritor.flush();
        escritor.close();
        for(int i = 1; i<=12;i++){
            File arq = new File("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\"+this.nome+"\\", "mes"+i+".txt");
            arq.createNewFile();
        }
    }
}
