package uminho.dss.turmas3l.business;
import uminho.dss.turmas3l.business.Gestao.Palete;

import java.util.Map;
import java.util.Set;

public interface IGestaoArmazemLNFacade {

    void adicionarPalete(Palete p);

    Set<Palete> getPaletes();
    Set<Palete> getPaletesZR();
    Set<Palete> getPaletesZA();
    Set<Palete> getPaletesZE();


    /*Map<String,Localizacao> solicitarListagem();
    void ordemTransporte(String idRobot, String idPalete);
    void comunicarQR(QRCode qr);
    void entregarPalete(String idRobot);
    void recolherPalete(String idRobot);*/
}
