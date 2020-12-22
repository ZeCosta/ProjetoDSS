package uminho.dss.turmas3l.business.Transporte;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;

public class Robot {
    private String id;
    private Estado estado;
    private Palete p;
    private Percurso per;
    private Localizacao l;

    public enum Estado{
        LIVRE, BUSCAR, TRANSPORTAR
    }

    public Robot(String id, Estado e, Palete p, Percurso per, Localizacao l){
        this.id = id;
        this.estado = e;
        this.p = p;
        this.per = per;
        this.l = l;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Palete getP() {
        return p;
    }

    public void setP(Palete p) {
        this.p = p;
    }

    public Percurso getPer() {
        return per;
    }

    public void setPer(Percurso per) {
        this.per = per;
    }

    public Localizacao getL() {
        return l;
    }

    public void setL(Localizacao l) {
        this.l = l;
    }
}
