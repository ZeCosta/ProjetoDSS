package uminho.dss.turmas3l.business.Gestao;

import java.util.Collection;
import java.util.TreeSet;

public class SubSistemaGestao implements IGest{
    private Collection<String> paletesIds;

    public SubSistemaGestao(){
        paletesIds = new TreeSet<>();
    }

    public SubSistemaGestao(Collection<String> ids){
        paletesIds = new TreeSet<>(ids);
    }

    public Collection<String> getPaletesIds(){
        return new TreeSet<>(paletesIds);
    }

    public void setPaletesIds(Collection<String> ids){
        paletesIds.addAll(ids);
    }

    public void addPaleteId(String id){
        paletesIds.add(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubSistemaGestao{");
        sb.append("paletesIds=").append(paletesIds);
        sb.append('}');
        return sb.toString();
    }
}
