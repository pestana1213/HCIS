import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HCIS {
    private Map<Quando, Float> gastoPorDia;

    public HCIS(){
        this.gastoPorDia = new HashMap<>();
    }

    public HCIS(Map<Quando,Float> gasto) {
        this.gastoPorDia = gasto;
    }

    public Map<Quando, Float> getGastoPorDia() {
        return gastoPorDia;
    }

    public void setGastoPorDia(Map<Quando, Float> gastoPorDia) {
        this.gastoPorDia = gastoPorDia;
    }

    public float getPossogastar(int dia, int mes){
        for(Quando quando : this.gastoPorDia.keySet()){
            if(quando.getMes()==mes&&quando.getDia()==dia){
                return quando.getPossogastar();
            }
        }
        return 0;
    }

    public void addQuando(Quando quando,float gasto){
        this.gastoPorDia.put(quando,gasto);
    }

    public int quantosdiastemomes(int mes) throws Exception {
        int dias = 31;
        if(mes>12||mes<1){
            throw new Exception("Mes desconhecido");
        }
        else{
            switch (mes) {
                case (2) -> dias -= 2;
                case (4), (6), (9), (11) -> dias--;
            }

        }
        return dias;
    }

    public String parseMes(int mes){
        switch (mes){
            case(1) -> {return "janeiro";}
            case(2) -> {return "fevereiro";}
            case(3) -> {return "março";}
            case(4) -> {return "abril";}
            case(5) -> {return "maio";}
            case(6) -> {return "junho";}
            case(7) -> {return "julho";}
            case(8) -> {return "agosto";}
            case(9) -> {return "setembro";}
            case(10) -> {return "outubro";}
            case(11) -> {return "novembro";}
            case(12) -> {return "dezembro";}
        }
        return "";
    }

    public float quantopossogastarnodia(Quando quando, User user,int disponivel) throws Exception {
        int diasnomes = quantosdiastemomes(quando.getMes());
        float dinheirodisponivelpordia = disponivel/diasnomes;
        float possogastar = quando.getPossogastar();
        int dia = quando.getDia()-1;
        int mes = quando.getMes();

        if (dia == 0){
            dia = 1;
            mes -= 1;
            if(mes == 0){
                mes = 1;
                dia = 31;
            }
            return dinheirodisponivelpordia;
        }
        int finalDia = dia;
        int finalMes = mes;
        Quando ant = null;
        if( this.gastoPorDia.keySet().stream().filter(e->e.getDia()== finalDia && e.getMes()== finalMes).collect(Collectors.toCollection(ArrayList::new)).size()>0){
            ant = this.gastoPorDia.keySet().stream().filter(e->e.getDia()== finalDia && e.getMes()== finalMes).collect(Collectors.toCollection(ArrayList::new)).get(0);
            float gastonodiaant = this.gastoPorDia.get(ant);
            return (dinheirodisponivelpordia + ant.getPossogastar()) - gastonodiaant;
        }
        else{
            return (dinheirodisponivelpordia);
        }

    }

    public void addgasto(Quando quando, float gasto){
        this.gastoPorDia.put(quando,gasto);
    }

    public void somagasto(int mes, int dia, float quanto) throws Exception {

        Quando quando = identificaData(mes, dia);

        if (quando != null && this.gastoPorDia.containsKey(quando)){
            float ov = this.gastoPorDia.get(quando);
            this.gastoPorDia.replace(quando,ov,ov+quanto);
        }
        else{
            throw new Exception("Data desconhecida");
        }
    }

    public Quando identificaData(int mes, int dia){
        for(Quando quando: this.gastoPorDia.keySet()){
            if(quando.getMes() == mes && quando.getDia()==dia){
                return quando;
            }
        }
        return null;
    }

    public float getGastoNoDia(int mes, int dia) throws Exception {
        if(this.gastoPorDia.keySet().stream().filter(e->e.getDia()==dia && e.getMes()==mes).collect(Collectors.toCollection(ArrayList::new)).size()>0){
            Quando quando = this.gastoPorDia.keySet().stream().filter(e->e.getDia()==dia && e.getMes()==mes).collect(Collectors.toCollection(ArrayList::new)).get(0);
            return this.gastoPorDia.get(quando);
        }
        else{
            throw new Exception();
        }
    }

    public void makeTables(){

        ArrayList<Quando> datas = new ArrayList<>(this.gastoPorDia.keySet());
        datas.sort(new Comparator());
        for(Quando quando : datas){
            float gasto = 0;
            if (this.gastoPorDia.get(quando) != null){
                gasto = this.gastoPorDia.get(quando);
            }
            System.out.println(quando.getDia() + "/" + quando.getMes() +": Podia gastar: " + quando.getPossogastar() + "Gasto: " + gasto);
        }
    }
}
