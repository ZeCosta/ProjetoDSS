package uminho.dss.turmas3l.business.Transporte;

public class Percurso {
    private String id;
    private String cRecolha; //Caminho até à zona de recolha
    private String cEntrega; //Caminho até à zona de entrega
    private String cRobots; //Caminho até à zona dos robots

    public Percurso(String id, String cR, String cE, String cRobots){
        this.id = id;
        cRecolha = cR;
        cEntrega = cE;
        this.cRobots = cRobots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcRecolha() {
        return cRecolha;
    }

    public void setcRecolha(String cRecolha) {
        this.cRecolha = cRecolha;
    }

    public String getcEntrega() {
        return cEntrega;
    }

    public void setcEntrega(String cEntrega) {
        this.cEntrega = cEntrega;
    }

    public String getcRobots() {
        return cRobots;
    }

    public void setcRobots(String cRobots) {
        this.cRobots = cRobots;
    }
}
