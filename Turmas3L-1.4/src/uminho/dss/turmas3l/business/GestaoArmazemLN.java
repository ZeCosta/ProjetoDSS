package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Gestao.SubSistemaGestao;
import uminho.dss.turmas3l.business.Transporte.SubSistemaTransporte;

import java.util.Set;

public class GestaoArmazemLN implements IGestaoArmazemLNFacade {
    private Armazem armazem = new Armazem();

    private SubSistemaGestao ssg = new SubSistemaGestao();
    private SubSistemaTransporte sst = new SubSistemaTransporte();

    public GestaoArmazemLN(){}

    /**
     * @param p palete a adicionar
     */
    @Override
    public void adicionarPalete(Palete p) {
        this.ssg.adicionarPalete(p);
    }

    @Override
    public Set<Palete> getPaletes() {
        return ssg.getPaletesZR();
    }
    @Override
    public Set<Palete> getPaletesZR() {
        return ssg.getPaletesZR();
    }
    @Override
    public Set<Palete> getPaletesZA() {
        return ssg.getPaletesZR();
    }
    @Override
    public Set<Palete> getPaletesZE() {
        return ssg.getPaletesZR();
    }
}
