package uminho.dss.turmas3l.business.Gestao;

import uminho.dss.turmas3l.business.Turma;
import uminho.dss.turmas3l.data.PaleteDAO;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SubSistemaGestao implements IGest{
    private Map<String, Palete> paletes;

    public SubSistemaGestao(){
        this.paletes = PaleteDAO.getInstance();
    }

    /*
    public void setPaletesIds(Collection<String> ids){
        paletes.addAll(ids);
    }

    public void addPaleteId(String id){
        paletes.add(id);
    }
    */

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubSistemaGestao{");
        sb.append("paletesIds=").append(paletes);
        sb.append('}');
        return sb.toString();
    }

    public void adicionarPalete(Palete p) {
        this.paletes.put(p.getId(), p);
    }

    public Set<Palete> getPaletes() {
        return (Set<Palete>) this.paletes.values();
    }
    public Set<Palete> getPaletesZR() {
        return (Set<Palete>) this.paletes.values();
    }
    public Set<Palete> getPaletesZA() {
        return (Set<Palete>) this.paletes.values();
    }
    public Set<Palete> getPaletesZE() {
        return (Set<Palete>) this.paletes.values();
    }
}
