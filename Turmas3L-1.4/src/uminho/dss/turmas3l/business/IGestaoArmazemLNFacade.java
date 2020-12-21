package uminho.dss.turmas3l.business;
import uminho.dss.turmas3l.business.Gestao.Palete;

import java.util.Map;

public interface IGestaoArmazemLNFacade {
    void adicionarPaleteZR(Palete p);
    /*Map<String,Localizacao> solicitarListagem();
    void ordemTransporte(String idRobot, String idPalete);
    void comunicarQR(QRCode qr);
    void entregarPalete(String idRobot);
    void recolherPalete(String idRobot);*/
}
