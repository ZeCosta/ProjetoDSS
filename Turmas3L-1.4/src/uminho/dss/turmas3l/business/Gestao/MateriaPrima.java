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
}
