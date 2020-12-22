package uminho.dss.turmas3l.business.Transporte;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;

public class Robot {
    private String id;
    private Estado estado;
    private Palete palete;
    private Percurso percurso;
    private Localizacao localizacao;

    public enum Estado{
        LIVRE, BUSCAR, TRANSPORTAR
    }

    public Robot(String id, Estado e, Palete p, Percurso per, Localizacao l){
        this.id = id;
        this.estado = e;
        this.palete = p;
        this.percurso = per;
        this.localizacao = l;
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

    public Palete getPalete() {
        return palete;
    }

    public void setPalete(Palete p) {
        this.palete = p;
    }

    public Percurso getPercurso() {
        return percurso;
    }

    public void setPercurso(Percurso per) {
        this.percurso = per;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao l) {
        this.localizacao = l;
    }
}
