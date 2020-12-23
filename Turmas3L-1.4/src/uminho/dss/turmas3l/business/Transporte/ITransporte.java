package uminho.dss.turmas3l.business.Transporte;

import uminho.dss.turmas3l.business.Gestao.Palete;

import java.util.Set;

public interface ITransporte {
    void comunicarTransporte(String id, Palete p, Percurso per);
    Robot getRobotDisponivel();

    Robot getRobot(String id);

    void addRobot(Robot r);

    void deleteRobot(String id);

    Set<Robot> getRobotComOrdens();

    boolean haRobot();

    void delAllRobots();

    Set<Robot> getRobots();

    boolean robotHasPalete(String id);
}
