package uminho.dss.turmas3l.business;

public class Localizacao {
    private String id;
    private String local;

    public Localizacao(String l){
        this.local=l;
    }

    public String getLocal(){
        return this.local;
    }

    public String getId(){return this.id;}
}
