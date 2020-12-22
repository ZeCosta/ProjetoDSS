package uminho.dss.turmas3l.business.Transporte;


import uminho.dss.turmas3l.business.Localizacao;
import uminho.dss.turmas3l.data.RobotDAO;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

public class SubSistemaTransporte {
    private Map<String, Robot> robots;

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

    public void mudaLocalizacao (String id, String l) {
        Localizacao loc = new Localizacao(l);
        this.getRobot(id).setLocalizacao(loc);
    }

    public void eliminaPalete (String id) {
        this.getRobot(id).setPalete(null);
    }

    public void mudaEstado (String id, String e) {
        this.getRobot(id).setEstado(e);
    }


}
