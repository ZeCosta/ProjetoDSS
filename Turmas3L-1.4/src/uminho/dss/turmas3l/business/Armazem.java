package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;
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
    private int mapa[][];
    private String[] locaisOrdenados;
    private Map<String,Localizacao> localizacoes;



    /*private int indiceLocal(String local, String[] locais){
        int i = 0, size = locais.length, res = -1;
        boolean stop = false;

        while(i < size && !stop){
            if(local.equals(locais[i])) {
                res = i;
                stop = true;
            }
            i++;
        }
        return res;
    }

    private boolean visitado(int i, int[] a, int n){
        boolean res = false;
        int pos = 0;
        while(pos < n && !res){
            if(i == a[pos])
                res = true;
            pos++;
        }
        return res;
    }

    private int procuraArco(int[] linha){
        int res = -1, i = 0;
        boolean stop = false;

        while(i < linha.length && !stop){
            if(linha[i] == 1){
                stop = true;
                res = i;
            }
            i++;
        }
        return res;
    }*/

    /*private String criaCaminho(int inicio, int fim, String[] locais, int[][] mapa){
        String caminho = locais[inicio];

        if(inicio == fim){
            return caminho;
        }else{
            int pivot = procuraArco(mapa[inicio]);
            return caminho + ":" + criaCaminho(pivot,fim,locais,mapa);
        }
    }*/

    public Armazem(){
        this.locaisOrdenados = new String[]{"ZRececao", "C1", "P1N", "P2N", "P3N", "P4N", "CP1N",
                "CP2N", "CP3N", "CP4N", "C2", "ZRobots", "CRobots", "CZEntrega", "ZEntrega", "C3",
                "P1S", "P2S", "P3S", "P4S", "CP1S", "CP2S", "CP3S", "CP4S", "C4"};
        this.mapa = new int[][]{
                         /*  ZRececao  C1 P1N P2N P3N P4N CP1N CP2N CP3N CP4N C2 ZRobots CRobots CZEntrega ZEntrega C3 P1S P2S P3S P4S CP1S CP2S CP3S CP4S C4*/
                /*ZRececao*/ {    0,    1, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*C1*/       {    1,    0, 0,  0,  0,  0,  1,   0,   0,   0,   0,   0,      1,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*P1N*/      {    0,    0, 0,  0,  0,  0,  1,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*P2N*/      {    0,    0, 0,  0,  0,  0,  0,   1,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*P3N*/      {    0,    0, 0,  0,  0,  0,  0,   0,   1,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*P4N*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   1,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*CP1N*/     {    0,    1, 1,  0,  0,  0,  0,   1,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*CP2N*/     {    0,    0, 0,  1,  0,  0,  1,   0,   1,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*CP3N*/     {    0,    0, 0,  0,  1,  0,  0,   1,   0,   1,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*CP4N*/     {    0,    0, 0,  0,  0,  1,  0,   0,   1,   0,   1,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*C2*/       {    0,    0, 0,  0,  0,  0,  0,   0,   0,   1,   0,   0,      0,       1,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*ZRobots*/  {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      1,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*CRobots*/  {    0,    1, 0,  0,  0,  0,  0,   0,   0,   0,   0,   1,      0,       0,        0,   1,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*CZEntrega*/{    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   1,   0,      0,       0,        1,   0,  0,  0,  0,  0,  0,   0,   0,   0,   1},
                /*ZEntrega*/ {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       1,        0,   0,  0,  0,  0,  0,  0,   0,   0,   0,   0},
                /*C3*/       {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      1,       0,        0,   0,  0,  0,  0,  0,  1,   0,   0,   0,   0},
                /*P1S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  1,   0,   0,   0,   0},
                /*P2S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   1,   0,   0,   0},
                /*P3S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   1,   0,   0},
                /*P4S*/      {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  0,  0,   0,   0,   1,   0},
                /*CP1S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   1,  1,  0,  0,  0,  0,   1,   0,   0,   0},
                /*CP2S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  1,  0,  0,  1,   0,   1,   0,   0},
                /*CP3S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  1,  0,  0,   1,   0,   1,   0},
                /*CP4S*/     {    0,    0, 0,  0,  0,  0,  0,   0,   0,   0,   0,   0,      0,       0,        0,   0,  0,  0,  0,  1,  0,   0,   1,   0,   1},
                /*C4*/       {    0,    0, 0,  0,  0,  0,  1,   0,   0,   0,   0,   0,      0,       1,        0,   0,  0,  0,  0,  0,  0,   0,   0,   1,   0}};
        this.localizacoes = LocalizacaoDAO.getInstance();
    }

    public String[] getLocaisOrdenados(){
        return locaisOrdenados;
    }

    public int[][] getMapa(){
        return mapa;
    }

    public Percurso criarPercurso(Localizacao lRobot, Localizacao lPalete, Localizacao destino, String idRobot){
        Percurso res = null;

        String localR = lRobot.getLocal();
        String localP = lPalete.getLocal();
        String dest = destino.getLocal();

        /*int iRobot = indiceLocal(localR, locaisOrdenados);
        int iPalete = indiceLocal(localP, locaisOrdenados);
        int iDest = indiceLocal(dest, locaisOrdenados);

        if(iRobot < 0 || iPalete < 0 || iDest < 0)
            return null;*/

        String cRecolha = localR + ":" + localP;
        String cEntrega = localP + ":" + dest;
        String cRobots = dest + ":" + "ZRobots";

        return new Percurso(idRobot,cRecolha,cEntrega,cRobots);
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


