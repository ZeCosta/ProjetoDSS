package uminho.dss.turmas3l.business;

import java.util.Collection;

/*
 * Esta classe representa o desenho ou mapa do armazém.
 * É definido por uma matriz baseada num grafo com 25 vértices, que representam pontos do armazém.
 * O grafo é apresentado e explicado no relatório, e sempre que existe um '1' na matriz, indica que existe caminho entre dois pontos do armazém.
 * As linhas e colunas representam os pontos do grafo, que são uma localização e definidos por um nome.
 */
public class Armazem {
    private int mapa[][];
    private String[] locaisOrdenados;
    /* Para povoar o armazém, podemos utilizar este mapa predefinido: */
    public int map[][] ={
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

    public Armazem(Collection<String> locais, int[][] mapa){
        this.locaisOrdenados = new String[locais.size()];
        int i = 0;
        for(String l : locais)
            this.locaisOrdenados[i++] = l;

        this.mapa = new int[locais.size()][locais.size()];
        this.mapa = mapa;
    }
    public Armazem(){
    }
    public String[] getLocaisOrdenados(){
        return locaisOrdenados;
    }

    public int[][] getMapa(){
        return mapa;
    }
}
