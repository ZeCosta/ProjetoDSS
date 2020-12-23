package uminho.dss.turmas3l.business.Gestao;

import java.util.Set;

public interface IGest {
    Set<Palete> getPaletesZR();

    Palete getPalete(String id);
    Set<Palete> getPaletesTrans();

    boolean haPalete();

    void delAllPaletes();

    void adicionarPalete(Palete p);

    Set<Palete> getPaletes();

    Set<Palete> getPaletesZA();

    Set<Palete> getPaletesZE();

    void eliminaLocalizacao(String id);
}
