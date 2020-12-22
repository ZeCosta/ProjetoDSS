package uminho.dss.turmas3l.business.Gestao;

import uminho.dss.turmas3l.business.Localizacao;

public class Palete {
    private String id;
    private double peso;
    private MateriaPrima materia;
    private Localizacao localizacao = null;

    public Palete(String id, double peso, MateriaPrima materia){
        this.id = id;
        this.materia = materia;
        this.peso = peso;
    }
    public Palete(String id, double peso, MateriaPrima materia,Localizacao localizacao){
        this.id = id;
        this.materia = materia;
        this.peso = peso;
        this.localizacao = localizacao;
    }

    public String getId(){
        return id;
    }

    public void setLocalizacao(Localizacao l){
        this.localizacao=l;
    }

    public Localizacao getLocalizacao(){
        return this.localizacao;
    }

    public MateriaPrima getMateriaPrima(){
        return this.materia;
    }


    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Palete{");
        sb.append("id='").append(id).append('\'');
        sb.append(", materia='").append(materia).append('\'');
        sb.append(", peso=").append(peso).append('\'');
        sb.append(", localizacao='").append(localizacao.getLocal());
        sb.append('}');
        return sb.toString();
    }
}
