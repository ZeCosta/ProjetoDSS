package uminho.dss.turmas3l.business;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Transporte.Robot;

import java.util.Map;
import java.util.Set;

public interface IGestaoArmazemLNFacade {

    void adicionarPalete(Palete p);

    Set<Palete> getPaletes();
    Set<Palete> getPaletesZR();
    Set<Palete> getPaletesZA();
    Set<Palete> getPaletesZE();

    Robot getRobot(String id);

    void addRobot(Robot r);

    void deleteRobot(String id);

    Palete getPalete(String id);

    boolean validaLocal(String destino);

    Robot getRobotDisponivel();

    void mudaLocalizacaoR (String id, String l);

    void eliminaPaleteR (String id);

    void mudaEstado (String id, String e);

    void eliminaLocalizacaoP (String id);


    /*Map<String,Localizacao> solicitarListagem();
    void ordemTransporte(String idRobot, String idPalete);
    void comunicarQR(QRCode qr);
    void entregarPalete(String idRobot);
    void recolherPalete(String idRobot);*/
}
