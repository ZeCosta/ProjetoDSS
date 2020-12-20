package uminho.dss.turmas3l.business.Gestao;
import java.util.ArrayList;
import java.util.Collection;

public class Palete {
    private String idPalete;
    private Collection<String> mats;
    private double peso;

    public Palete(String id, double peso){
        this.idPalete = id;
        this.mats = null;
        this.peso = peso;
    }

    public Palete(String id, Collection<String> mats, double peso) {
        this.idPalete = id;
        this.mats = new ArrayList<>();
        this.mats.addAll(mats);
        this.peso = peso;
    }

    public String getId(){
        return this.idPalete;
    }
}
