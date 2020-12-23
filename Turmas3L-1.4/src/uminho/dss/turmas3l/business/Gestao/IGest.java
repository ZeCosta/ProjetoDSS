package uminho.dss.turmas3l.business.Gestao;
import uminho.dss.turmas3l.business.Localizacao;
import uminho.dss.turmas3l.business.QRCode;
import uminho.dss.turmas3l.business.Transporte.Robot;

import java.util.Map;
import java.util.Set;

public interface IGest {
    /*Map<String, Localizacao> solicitarListagem();
    void comunicarQR(QRCode qr);
    */

    Set<Palete> getPaletesZR();

    Palete getPalete(String id);
    Set<Palete> getPaletesTrans();

}
