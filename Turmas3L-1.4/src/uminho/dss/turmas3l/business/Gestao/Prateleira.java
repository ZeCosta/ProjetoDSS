package uminho.dss.turmas3l.business.Gestao;
import uminho.dss.turmas3l.business.Localizacao;

import java.util.ArrayList;
import java.util.Collection;

public class Prateleira {
    private Localizacao l;
    private Collection<String> paletesIds;

    public Prateleira(Localizacao l){
        this.l = l;
        this.paletesIds = new ArrayList<>();
    }

    public Prateleira(Localizacao l, Collection<String> ids){
        this.l = l;
        paletesIds = new ArrayList<>();
        paletesIds.addAll(ids);
    }

    public void setLocalizacao(Localizacao l){
        this.l = l;
    }

    public void setPaletesIds(Collection<String> ids){
        this.paletesIds.addAll(ids);
    }
}
