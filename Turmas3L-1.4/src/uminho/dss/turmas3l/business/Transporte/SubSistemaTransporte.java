package uminho.dss.turmas3l.business.Transporte;


import uminho.dss.turmas3l.business.Localizacao;
import uminho.dss.turmas3l.data.RobotDAO;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

public class SubSistemaTransporte {
    private Map<String, Robot> robots;


    private int indiceLocal(String local, String[] locais){
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
    }

    private String criaCaminho(int inicio, int fim, String[] locais, int[][] mapa){
        int iPivot = inicio;
        String caminho = locais[inicio];

        while(iPivot != fim){
            int[] linhaPivot = mapa[iPivot];
            int iSeguinte = procuraArco(linhaPivot);
            caminho = caminho + ":" + locais[iSeguinte];
            iPivot = iSeguinte;
        }

        return caminho;
    }

    public SubSistemaTransporte(){
        this.robots = RobotDAO.getInstance();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubSistemaTransporte{");
        sb.append("robotsIds=").append(robots);
        sb.append('}');
        return sb.toString();
    }

    public Robot getRobot(String id) {
        return this.robots.get(id);
    }

    public void addRobot(Robot r) {
        this.robots.put(r.getId(),r);
    }

    public void deleteRobot(String id) {
        this.robots.remove(id);
    }

    public Percurso criarPercurso(String idRobot, Localizacao lPalete, Localizacao destino, String[] locais, int[][] mapa){
        Percurso res = null;

        Robot r = this.robots.get(idRobot);

        String localR = r.getLocalizacao().getLocal();
        String localP = lPalete.getLocal();
        String dest = destino.getLocal();

        int iRobot = indiceLocal(localR, locais);
        int iPalete = indiceLocal(localP, locais);
        int iDest = indiceLocal(dest, locais);

        String cRecolha = criaCaminho(iRobot, iPalete, locais, mapa);
        String cEntrega = criaCaminho(iPalete, iDest, locais, mapa);
        String cRobots = criaCaminho(iDest, iRobot, locais, mapa);

        /* FALTA CRIAR ID DO PERCURSO */

        res = new Percurso("3213213",cRecolha,cEntrega,cRobots);
        return res;
    }
}
