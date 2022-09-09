import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Map<User, HCIS> users;

    public Map<User,HCIS> getUsers(){
        return this.users;
    }

    public Main() {
        this.users = new HashMap<>();
    }

    public void addUser(User u,HCIS s){
        this.users.put(u,s);
    }

    public boolean login(String nome, String pass) {

        for(User u : this.users.keySet()){
            if(u.getNome().equals(nome) && u.getPassword().equals(pass)){
                return true;
            }
        }
        return false;
    }

    public void registarUser(String nome, String pass,int salario,int poupar) throws Exception {
        int id = generateId();
        if(this.users!=null){
            if (this.users.keySet().stream().filter(e->e.getNome().equals(nome)).collect(Collectors.toCollection(ArrayList::new)).size()>0){
                throw new Exception("Nome ja registado");
            }
            else{
                User u = new User(nome,pass,salario,poupar,id);
                HCIS s = new HCIS();
                this.users.put(u,s);
                u.guarda();
            }
        }
        else{
            User u = new User(nome,pass,salario,poupar,id);
            HCIS s = new HCIS();
            addUser(u,s);
            u.guarda();
        }
    }

    public User identifica(String nome){
        return this.users.keySet().stream().filter(e->e.getNome().equals(nome)).collect(Collectors.toCollection(ArrayList::new)).get(0);
    }

    public int generateId(){
        int maior = 0;

        if(this.users!=null){
            ArrayList<Integer> ids = this.users.keySet().stream().map(User::getId).collect(Collectors.toCollection(ArrayList::new));
            for(Integer i :ids){
                if (i>=maior){
                    maior = i;
                }
            }
        }

        return maior+=1;
    }

    public void save(){
        this.users.values().forEach(HCIS::save);
    }

    public void addgasto(User u, int mes, int dia, int quanto) throws Exception {
        HCIS aux = this.users.get(u);
        aux.somagasto(mes,dia,quanto);
    }
}
