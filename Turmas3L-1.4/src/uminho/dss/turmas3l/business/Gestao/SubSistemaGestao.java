package uminho.dss.turmas3l.business.Gestao;

import uminho.dss.turmas3l.data.PaleteDAO;

import java.util.*;

public class SubSistemaGestao implements IGest{
    private Map<String, Palete> paletes;

    public SubSistemaGestao(){
        this.paletes = PaleteDAO.getInstance();
    }

    @Override
    public Palete getPalete(String id) {
        return this.paletes.get(id);
    }

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
        Set<Palete> res = new HashSet<>();
        for(Palete p:this.paletes.values()){
            if(p.getLocalizacao()!=null &&
                    p.getLocalizacao().getLocal().equals("ZRececao")) {
                res.add(p);
            }
        }
        return res;
    }
    public Set<Palete> getPaletesZA() {
        Set<Palete> res = new HashSet<>();
        for(Palete p:this.paletes.values()){
            if(p.getLocalizacao()!=null &&
                    p.getLocalizacao().getLocal()!=null
                    && !p.getLocalizacao().getLocal().equals("ZRececao")
                    && !p.getLocalizacao().getLocal().equals("ZEntrega")) res.add(p);
        }
        return res;
    }
    public Set<Palete> getPaletesZE() {
        Set<Palete> res = new HashSet<>();
        for(Palete p:this.paletes.values()){
            if(p.getLocalizacao()!=null &&
                    p.getLocalizacao().getLocal().equals("ZEntrega")) res.add(p);
        }
        return res;
    }
    public Set<Palete> getPaletesTrans() {
        Set<Palete> res = new HashSet<>();
        for(Palete p:this.paletes.values()){
            if(p.getLocalizacao()==null) res.add(p);
        }
        return res;
    }

    @Override
    public boolean haPalete() {
        return !this.paletes.isEmpty();
    }

    @Override
    public void delAllPaletes() {
        this.paletes.clear();
    }


    public void eliminaLocalizacao (String id) {
        this.getPalete(id).setLocalizacao(null);
    }
}
