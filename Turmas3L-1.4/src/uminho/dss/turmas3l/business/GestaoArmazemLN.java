package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.SubSistemaGestao;
import uminho.dss.turmas3l.business.Transporte.SubSistemaTransporte;

public class GestaoArmazemLN implements IGestaoArmazemLNFacade {
    private Armazem armazem = new Armazem();

    private SubSistemaGestao ssg = new SubSistemaGestao();
    private SubSistemaTransporte sst = new SubSistemaTransporte();

    public GestaoArmazemLN(){

    }
}
