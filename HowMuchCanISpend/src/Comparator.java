public class Comparator implements java.util.Comparator<Quando> {
    @Override
    public int compare(Quando o1, Quando o2) {

        if(o1.getMes() == o2.getMes()){
            return  o1.getDia()-o2.getDia();
        }
        else{
            return o1.getMes()-o2.getMes();
        }
    }
}
