package uminho.dss.turmas3l.business.Gestao;

public class Palete {
    private String matId;
    private String id;
    private double peso;

    public Palete(String id, double peso, String matId){
        this.id = id;
        this.matId = matId;
        this.peso = peso;
    }

    public String getId(){
        return id;
    }

    public String getMatId() {
        return matId;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Palete{");
        sb.append("matId='").append(matId).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", peso=").append(peso);
        sb.append('}');
        return sb.toString();
    }
}
