package uminho.dss.turmas3l.business.Transporte;

public class Robot {
    private String id;
    private Estado estado;
    private String paleteId;
    private String percursoId;

    public enum Estado{
        LIVRE, BUSCAR, TRANSPORTAR
    }

    public Robot(String id){
        this.id = id;
        this.estado = Estado.LIVRE;
        this.paleteId = null;
    }

    public Robot(String id, Estado e, String idPalete, String percursoId){
        this.id = id;
        this.estado = e;
        this.paleteId = idPalete;
        this.percursoId = percursoId;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public void setPaleteId(String paleteId){
        this.paleteId = paleteId;
    }

    public void setPercursoId(String id){
        this.percursoId = id;
    }

    public String getId() {
        return id;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getPaleteId() {
        return paleteId;
    }

    public String getPercursoId() {
        return percursoId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Robot{");
        sb.append("id='").append(id).append('\'');
        sb.append(", estado=").append(estado);
        sb.append(", paleteId='").append(paleteId).append('\'');
        sb.append(", percursoId='").append(percursoId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
