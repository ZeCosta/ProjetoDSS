package uminho.dss.turmas3l.business;

public class Aresta {
    private String id; //v1+v2
    private Localizacao v1;
    private Localizacao v2;

    public Aresta(String id,Localizacao v1, Localizacao v2){
        this.id=id;
        this.v1=v1;
        this.v2=v2;
    }

    public Localizacao getV1(){
        return this.v1;
    }
    public Localizacao getV2(){
        return this.v2;
    }

    public String getId() {
        return this.id;
    }
}
