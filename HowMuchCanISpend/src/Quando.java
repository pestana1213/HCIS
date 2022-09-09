import java.util.Objects;

public class Quando {

    private int mes;
    private int dia;
    private float possogastar;

    public Quando(){
        this.mes = 1;
        this.dia = 1;
        this.possogastar = 0;
    }
    public Quando(int mes, int dia) {
        this.mes = mes;
        this.dia = dia;
        this.possogastar = 0;
    }

    public Quando(int mes, int dia, float possogastar) {
        this.mes = mes;
        this.dia = dia;
        this.possogastar = possogastar;
    }


    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public float getPossogastar() {
        return possogastar;
    }

    public void setPossogastar(float possogastar) {
        this.possogastar = possogastar;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quando quando = (Quando) o;
        return mes == quando.mes && dia == quando.dia && possogastar == quando.possogastar;
    }

    public int hashCode() {
        return Objects.hash(mes, dia,possogastar);
    }
}
