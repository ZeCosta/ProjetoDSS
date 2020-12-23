package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;
import uminho.dss.turmas3l.data.ArestaDAO;
import uminho.dss.turmas3l.data.LocalizacaoDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/*
 * Esta classe representa o desenho ou mapa do armazém.
 * É definido por uma matriz baseada num grafo com 25 vértices, que representam pontos do armazém.
 * O grafo é apresentado e explicado no relatório, e sempre que existe um '1' na matriz, indica que existe caminho entre dois pontos do armazém.
 * As linhas e colunas representam os pontos do grafo, que são uma localização e definidos por um nome.
 */
public class Armazem {
    //private int mapa[][];
    //private String[] locaisOrdenados;
    private Map<String,Localizacao> localizacoes;
    private Map<String,Aresta> arestas;


    public Armazem(){
       /* this.locaisOrdenados = new String[]{"ZRececao", "C1", "P1N", "P2N", "P3N", "P4N", "CP1N",
                "CP2N", "CP3N", "CP4N", "C2", "ZRobots", "CRobots", "CZEntrega", "ZEntrega", "C3",
                "P1S", "P2S", "P3S", "P4S", "CP1S", "CP2S", "CP3S", "CP4S", "C4"};
        */

        //this.mapa = new int[][]{
                         //  ZRececao  C1 P1N P2N P3N P4N CP1N CP2N CP3N CP4N C2 ZRobots CRobots CZEntrega ZEntrega C3 P1S P2S P3S P4S CP1S CP2S CP3S CP4S C4
                //ZRececao {    0,    1, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //C1       {    1,    0, 0,  0,  0,  0,  1,   0,   0,   0,   0,   0,      1,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //P1N      {    0,    0, 0,  0,  0,  0,  1,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //P2N      {    0,    0, 0,  0,  0,  0,  0,   1,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //P3N      {    0,    0, 0,  0,  0,  0,  0,   0,   1,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //P4N      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   1,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //CP1N     {    0,    1, 1,  0,  0,  0,  0,   1,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //CP2N     {    0,    0, 0,  1,  0,  0,  1,   0,   1,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //CP3N     {    0,    0, 0,  0,  1,  0,  0,   1,   0,   1,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //CP4N     {    0,    0, 0,  0,  0,  1,  0,   0,   1,   0,   1,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //C2       {    0,    0, 0,  0,  0,  0,  0,   0,   0,   1,   0,   0,      0,       1,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //ZRobots*/  {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      1,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //CRobots*/  {    0,    1, 0,  0,  0,  0,  0,   0,   0,   0,   0,   1,      0,       0,        0,   1,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //CZEntrega*/{    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   1,   0,      0,       0,        1,   0,  0,  0,  0,  0,  0,   0,   0,   0,   1},
                //ZEntrega*/ {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       1,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                //C3*/       {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      1,       0,        0,   0,  0,  0,  0,  0,  1,   0,   0,   0,   0},
                //P1S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  1,   0,   0,   0,   0},
                //P2S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   1,   0,   0,   0},
                //P3S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   1,   0,   0},
                //P4S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   1,   0},
                //CP1S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   1,  1,  0,  0,  0,  0,   1,   0,   0,   0},
                //CP2S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  1,  0,  0,  1,   0,   1,   0,   0},
                //CP3S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  1,  0,  0,   1,   0,   1,   0},
                //CP4S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  1,  0,   0,   1,   0,   1},
                //C4*/       {    0,    0, 0,  0,  0,  0,  1,   0,   0,   0,   0,   0,      0,       1,        0,   0,  0,  0,  0,  0,  0,   0,   0,   1,   0}};
        this.localizacoes = LocalizacaoDAO.getInstance();
        this.arestas = ArestaDAO.getInstance();
    }

    public String dijkstra(Localizacao a,Localizacao b){        //Por implementar -> usar dijkstra
        return a.getLocal() + ":" + b.getLocal();
    }

    public Percurso criarPercurso(Localizacao lRobot, Localizacao lPalete, Localizacao destino, String idRobot){
        Percurso res = null;

        String cRecolha = this.dijkstra(lRobot,lPalete);
        String cEntrega = this.dijkstra(lPalete,destino);

        String cRobots = null;
        if(this.validaLocalizacao("ZRobots")) cRobots = this.dijkstra(destino,new Localizacao("ZRobots"));

        if(cRecolha!=null && cEntrega!=null && cRobots!=null)
              res = new Percurso(idRobot,cRecolha,cEntrega,cRobots);

        return res;
    }


    public boolean validaLocalizacao(String l){
        boolean res=false;
        for(Localizacao local:this.localizacoes.values()){
            if (local.getLocal().equals(l)) {
                res = true;
                break;
            }
        }

        return res;
    }

    public void putAllLocalizacoes(String[] s){
        for(String si:s){
            this.localizacoes.put(si,new Localizacao(si));
        }
    }

    public void delAllLocalizacoes() {
        this.localizacoes.clear();
    }

    public void delAllArestas() {
        this.arestas.clear();
    }

    public void criarArmazemDefault() {
        //localizacoes
        String[] s = new String[]{"ZRececao", "C1", "P1N", "P2N", "P3N", "P4N", "CP1N",
                "CP2N", "CP3N", "CP4N", "C2", "ZRobots", "CRobots", "CZEntrega", "ZEntrega", "C3",
                "P1S", "P2S", "P3S", "P4S", "CP1S", "CP2S", "CP3S", "CP4S", "C4"};
        for(String si:s){
            this.localizacoes.put(si,new Localizacao(si));
        }

        //arestas
        Aresta a = new Aresta("",new Localizacao("ZRececao"),new Localizacao("C1"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("C1"),new Localizacao("ZRececao"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("C1"),new Localizacao("CP1N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP1N"),new Localizacao("P1N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("C1ZRececao",new Localizacao("P1N"),new Localizacao("CP1N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP1N"),new Localizacao("CP2N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP2N"),new Localizacao("P2N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P2N"),new Localizacao("CP2N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP2N"),new Localizacao("CP3N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);


        a = new Aresta("",new Localizacao("CP3N"),new Localizacao("P3N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P3N"),new Localizacao("CP3N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP3N"),new Localizacao("CP4N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);


        a = new Aresta("",new Localizacao("CP4N"),new Localizacao("P4N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P4N"),new Localizacao("CP4N"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);


        a = new Aresta("",new Localizacao("CP4N"),new Localizacao("C2"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("C2"),new Localizacao("CZEntrega"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CZEntrega"),new Localizacao("ZEntrega"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("ZEntrega"),new Localizacao("CZEntrega"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CZEntrega"),new Localizacao("C4"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("C4"),new Localizacao("CP4S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP4S"),new Localizacao("P4S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P4S"),new Localizacao("CP4S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP4S"),new Localizacao("CP3S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP3S"),new Localizacao("P3S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P3S"),new Localizacao("CP3S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP3S"),new Localizacao("CP2S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP2S"),new Localizacao("P2S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P2S"),new Localizacao("CP2S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP2S"),new Localizacao("CP1S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP1S"),new Localizacao("P1S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("P1S"),new Localizacao("CP1S"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("CP1S"),new Localizacao("C3"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("C3"),new Localizacao("CRobots"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);

        a = new Aresta("",new Localizacao("ZRobots"),new Localizacao("CRobots"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("CRobots"),new Localizacao("ZRobots"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());

        this.arestas.put(a.getId(),a);
        a = new Aresta("",new Localizacao("CRobots"),new Localizacao("C1"));
        a.setId(a.getV1().getLocal()+a.getV2().getLocal());
        this.arestas.put(a.getId(),a);
    }
    /*
    public static void main(String[] args){
        System.out.println("Ola");
        Armazem a = new Armazem();
        Localizacao lPalete = new Localizacao("P2N");
        Localizacao lRobot = new Localizacao("ZRobots");
        Percurso p = a.criarPercurso(lRobot, lPalete, new Localizacao("P3S"), "1");
        System.out.println(p.toString());
        System.out.println("Adeus");
    }
    */
}


