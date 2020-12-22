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
        LIVRE, BUSCAR, TRANSPORTAR, REGRESSAR
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

    public String getEstado() {
        return this.estado.name();
    }

    public void setEstado(String estado) {
        this.estado = Estado.valueOf(estado);
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

    public String toString() {
        final StringBuilder sb = new StringBuilder("Robot{");
        sb.append("id='").append(id).append('\'');
        sb.append(", Estado='").append(this.getEstado()).append('\'');
        sb.append(", Palete=").append(this.getPalete().getId()).append('\'');
        sb.append(", Percurso=").append(this.getPercurso().getId()).append('\'');
        sb.append(", localizacao='").append(localizacao.getLocal());
        sb.append('}');
        return sb.toString();
    }
}
