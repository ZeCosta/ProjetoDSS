package uminho.dss.turmas3l.business.Transporte;


import uminho.dss.turmas3l.business.Gestao.IGest;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;
import uminho.dss.turmas3l.data.RobotDAO;

import java.util.*;

public class SubSistemaTransporte implements ITransporte {
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


    public void comunicarTransporte(String id, Palete p, Percurso per) {
        Robot r = this.getRobot(id);
        r.setPercurso(per);
        r.setPalete(p);
        r.setEstado("BUSCAR");
        Robot res = this.robots.put(id,r);
    }

    public Robot getRobotDisponivel() {
        for(Robot r:this.robots.values()){
            if(r.getEstado().equals("LIVRE")){
                return r;
            }
        }
        return null;
    }

    public Set<Robot> getRobotComOrdens() {
        Set<Robot> res = new HashSet<>();
        for(Robot r:this.robots.values()){
            if(r.getEstado().equals("BUSCAR")||r.getEstado().equals("TRANSPORTAR")) res.add(r);
        }
        return res;
    }

    public boolean haRobot() {
        return !this.robots.isEmpty();
    }

    public void delAllRobots() {
        this.robots.clear();
    }

    public Set<Robot> getRobots() {
        return new HashSet<>(this.robots.values());
    }

    public boolean robotHasPalete(String id) {
        boolean res=false;
        for(Robot r:this.robots.values()){
            if(r.getPalete()!=null && r.getPalete().getId().equals(id)) res=true;
        }
        return res;
    }
}
