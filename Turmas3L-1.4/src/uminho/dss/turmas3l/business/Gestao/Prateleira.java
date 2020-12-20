package uminho.dss.turmas3l.business.Gestao;
import java.util.ArrayList;
import java.util.Collection;

public class Prateleira {
    private String idPrat;
    private double pesoMax;
    private int paletesMax;
    private Collection<String> paletes;

    public Prateleira(String id, double peso, int nPaletes){
        idPrat = id;
        pesoMax = peso;
        paletesMax = nPaletes;
        paletes = new ArrayList<>();
    }

    public Prateleira(String id, double peso, int nPaletes, Collection<String> ids){
        idPrat = id;
        pesoMax = peso;
        paletesMax = nPaletes;
        paletes = new ArrayList<>();
        paletes.addAll(ids);
    }
}
