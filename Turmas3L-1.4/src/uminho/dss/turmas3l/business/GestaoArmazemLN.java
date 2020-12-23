package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Gestao.SubSistemaGestao;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;
import uminho.dss.turmas3l.business.Transporte.SubSistemaTransporte;

import java.util.HashSet;
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
    public Palete getPalete(String id) {
        return ssg.getPalete(id);
    }


    @Override
    public Set<Palete> getPaletes() {
        return ssg.getPaletes();
    }
    @Override
    public Set<Palete> getPaletesZR() {
        return ssg.getPaletesZR();
    }
    @Override
    public Set<Palete> getPaletesZA() {
        return ssg.getPaletesZA();
    }

    @Override
    public Set<Palete> getPaletesZE() {
        return ssg.getPaletesZE();
    }

    @Override
    public Set<Palete> getPaletesTrans() {
        return ssg.getPaletesZE();
    }



    @Override
    public Robot getRobot(String id) {
        return this.sst.getRobot(id);
    }

    @Override
    public void addRobot(Robot r) {
        this.sst.addRobot(r);
    }

    public void deleteRobot(String id) {
        this.sst.deleteRobot(id);
    }


    @Override
    public boolean validaLocal(String destino) {    //fazer funcao -> ir ao armazem e verificar que local existe
        return true;
    }

    @Override
    public Robot getRobotDisponivel() {
        return null;
    }

    public void mudaLocalizacaoR (String id, String l) {
        this.sst.mudaLocalizacao (id, l);
    }

    public void eliminaPaleteR (String id) {
        this.sst.eliminaPalete (id);
    }

    public void mudaEstado (String id, String e) {
        this.sst.mudaEstado (id, e);
    }

    public void eliminaLocalizacaoP (String id) {
        this.ssg.eliminaLocalizacao (id);
    }

    @Override
    public Percurso getPercurso(Localizacao lRobot, Localizacao lPalete, Localizacao destino, String idRobot) {
        return this.armazem.criarPercurso(lRobot, lPalete, destino, idRobot);
    }

    @Override
    public void comunicarTransporte(Robot r, Palete p, Localizacao destino) {
        Percurso per = this.getPercurso(r.getLocalizacao(), p.getLocalizacao(),
                destino, r.getId());
        this.sst.comunicarTransporte(r.getId(), p, per);
    }
}
