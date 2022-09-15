import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static void parseUsers(Main main){
        User j = new User();
        List<String> linhas = lerFicheiro("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\logins");
        String[] linhaPartida;

        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            j.setNome(linhaPartida[0]);
            j = User.parse(linhaPartida[1],linhaPartida[0]);
            HCIS s = parseTabela(j);
            main.addUser(j,s);
        }
    }

    public static HCIS parseTabela(User u){
        Map<Quando,Float> teste = new HashMap<>();

        for(int i = 1; i<=12;i++){
            List<String> linhas = lerFicheiro("C:\\Users\\Pestana\\Desktop\\HowMuchCanISpend\\logs\\" + u.getNome()
            +"\\mes" + i+".txt");
            HCIS novo = new HCIS();
            Quando quando = new Quando();
            String[] linhaPartida;
            for (String linha : linhas) {
                linhaPartida = linha.split(":", 2);
                int dia = Integer.parseInt(linhaPartida[0]);
                String[] campos = linhaPartida[1].split(",");
                float podegastar = Float.parseFloat(campos[0]);
                float gasto = 0;
                if (campos.length>1){
                    gasto = Float.parseFloat(campos[1]);
                }
                quando = new Quando(i,dia,podegastar);
                teste.put(quando,gasto);
            }
        }

        return new HCIS(teste);
    }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }
}

