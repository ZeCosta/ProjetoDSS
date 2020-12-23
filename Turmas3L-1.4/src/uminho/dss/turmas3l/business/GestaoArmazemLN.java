package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.IGest;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Gestao.SubSistemaGestao;
import uminho.dss.turmas3l.business.Transporte.ITransporte;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;
import uminho.dss.turmas3l.business.Transporte.SubSistemaTransporte;

import java.util.Set;

public class GestaoArmazemLN implements IGestaoArmazemLNFacade {
    private Armazem armazem = new Armazem();

    private IGest ssg = new SubSistemaGestao();
    private ITransporte sst = new SubSistemaTransporte();

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
        return ssg.getPaletesTrans();
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
        return this.armazem.validaLocalizacao(destino);
    }

    @Override
    public Robot getRobotDisponivel(){
        return this.sst.getRobotDisponivel();
    }


    @Override
    public void comunicarTransporte(Robot r, Palete p, Localizacao destino) {
        Percurso per = this.armazem.criarPercurso(r.getLocalizacao(), p.getLocalizacao(),destino, r.getId());
        if(per!=null) this.sst.comunicarTransporte(r.getId(), p, per);
    }


    @Override
    public Set<Robot> getRobotComOrdens() {
        return this.sst.getRobotComOrdens();
    }

    @Override
    public boolean haRobot() {
        return this.sst.haRobot();
    }

    @Override
    public boolean haPalete() {
        return this.ssg.haPalete();
    }

    @Override
    public void delAllRobots() {
        this.sst.delAllRobots();
    }

    @Override
    public void delAllPaletes() {
        this.ssg.delAllPaletes();
    }

    @Override
    public void delAllLocalizacoes() {
        this.armazem.delAllLocalizacoes();
    }
    @Override
    public void delAllArestas() {
        this.armazem.delAllArestas();
    }

    @Override
    public void criarArmazemDefault() {
        this.armazem.criarArmazemDefault();
    }

    @Override
    public Set<Robot> getRobots() {
        return this.sst.getRobots();
    }

    @Override
    public boolean robotHasPalete(String id) {
        return this.sst.robotHasPalete(id);
    }

}
