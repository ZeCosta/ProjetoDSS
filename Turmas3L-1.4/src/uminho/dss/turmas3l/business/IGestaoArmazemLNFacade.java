package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Transporte.Robot;

import java.util.Set;

public interface IGestaoArmazemLNFacade {

    void adicionarPalete(Palete p);

    Set<Palete> getPaletes();
    Set<Palete> getPaletesZR();
    Set<Palete> getPaletesZA();
    Set<Palete> getPaletesZE();
    Set<Palete> getPaletesTrans();

    Robot getRobot(String id);

    void addRobot(Robot r);

    void deleteRobot(String id);

    Palete getPalete(String id);

    boolean validaLocal(String destino);

    Robot getRobotDisponivel();


    void comunicarTransporte(Robot r, Palete p, Localizacao destino);

    Set<Robot> getRobotComOrdens();

    boolean haRobot();

    boolean haPalete();

    void delAllRobots();

    void delAllPaletes();

    void delAllLocalizacoes();
    void delAllArestas();

    void criarArmazemDefault();

    Set<Robot> getRobots();

    boolean robotHasPalete(String id);

}
