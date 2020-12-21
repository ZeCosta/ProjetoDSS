package uminho.dss.turmas3l.business.Gestao;
import java.util.ArrayList;
import java.util.Collection;

public class Palete {
    private String idPalete;
    private MateriaPrima materia;
    private double peso;

    public Palete(String id, double peso){
        this.idPalete = id;
        this.materia = null;
        this.peso = peso;
    }

    public Palete(String id, MateriaPrima mats, double peso) {
        this.idPalete = id;
        this.materia = mats;
        this.peso = peso;
    }

    public String getId(){
        return this.idPalete;
    }
}
