package uminho.dss.turmas3l.business.Transporte;

public class Robot {
    private String id;
    private Estado estado;
    private String paleteId;

    public enum Estado{
        LIVRE, OCUPADO
    }

    public Robot(String id){
        this.id = id;
        this.estado = Estado.LIVRE;
        this.paleteId = null;
    }

    public Robot(String id, Estado e, String idPalete){
        this.id = id;
        this.estado = e;
        this.paleteId = idPalete;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public void setPaleteId(String paleteId){
        this.paleteId = paleteId;
    }
}
