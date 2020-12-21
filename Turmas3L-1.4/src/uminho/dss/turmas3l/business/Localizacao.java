package uminho.dss.turmas3l.business;

public class Localizacao {
    private String local;

    public enum Local{
        P1N, P2N, P3N, P4N,
        P1S, P2S, P3S, P4S,
        ZRececao, ZEntrega, ZRobots,
        C1, C2, C3, C4,
        CRobots, CZEntrega,
        CP1N, CP2N, CP3N, CP4N,
        CP1S, CP2S, CP3S, CP4S
    }

    public Localizacao(String l){
        this.local=l;
    }

    public String getLocal(){
        return this.local;
    }
}
