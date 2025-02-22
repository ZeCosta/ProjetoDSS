package uminho.dss.turmas3l.business.Gestao;

public class MateriaPrima {
    private String id;
    private String nome;
    private double peso;
    private int qtd;

    public MateriaPrima(String id, String nome, double peso){
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.qtd = 1;
    }

    public MateriaPrima(String id, String nome, double peso, int qtd){
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.qtd = qtd;
    }

    public void setQtd(int qtd){
        this.qtd = qtd;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }

    public int getQtd() {
        return qtd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MateriaPrima{");
        sb.append("id='").append(id).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", peso=").append(peso);
        sb.append(", qtd=").append(qtd);
        sb.append('}');
        return sb.toString();
    }
}
