/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.ui;

import uminho.dss.turmas3l.business.*;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;

import java.util.Scanner;

/**
 * Exemplo de interface em modo texto.
 *
 * @author JFC
 * @version 20201208
 */
public class UIText {
    // O model tem a 'lógica de negócio'.
    private IGestaoArmazemLNFacade model;

    // Scanner para leitura
    private Scanner scin;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public UIText() {

        this.model = new GestaoArmazemLN();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Sistema de Gestão de Armazém!");
        this.criarArmazem();
        this.menuPrincipal();
        System.out.println("Até breve...");
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Menu Principal
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "Comunicar QRcode",
                "Comunicar ordem de transporte",
                "Consultar Listagens",
                "Operações sobre robots",
                "Outras opções"
        });

        // Registar pré-condições das transições
        menu.setPreCondition(2, ()->this.model.haRobot() && this.model.haPalete());
        menu.setPreCondition(3, ()->this.model.haPalete());
        menu.setPreCondition(4, ()->this.model.haRobot());
        //menu.setPreCondition(4, ()->this.model.haRobot());

        // Registar os handlers
        menu.setHandler(1, ()->comunicarQRCode());
        menu.setHandler(2,()->comunicarOrdemTransporte());
        menu.setHandler(3, ()->consultarListagem());
        menu.setHandler(4, ()->gestaoRobots());
        menu.setHandler(5, ()->gestaoDeOpcoes());

        menu.run();
    }

    /**
     *  Estado -
     */
    private void consultarListagem() {
        Menu menu = new Menu(new String[]{
                "Consultar tudo",
                "Consultar zona de receção",
                "Consultar zona de armazenamento",
                "Consultar zona de entrega",
                "Consultar as que estão a ser transportadas"
        });

        // Registar os handlers
        menu.setHandler(1, ()->consultarTudo());
        menu.setHandler(2, ()->consultarZonaRececao());
        menu.setHandler(3, ()->consultarZonaArmazenamento());
        menu.setHandler(4, ()->consultarZonaEntrega());
        menu.setHandler(5, ()->consultarTransportar());

        menu.run();
    }

    /**
     *  Estado -
     */
    private void gestaoRobots() {
        Menu menu = new Menu(new String[]{
                "Listar ordens de transporte",
                "Notificar recolha de palete",
                "Notificar entrega de palete"
        });

        // Registar os handlers
        menu.setHandler(1, ()->listarOrdens());
        menu.setHandler(2, ()->notificarRecolha());
        menu.setHandler(3, ()->notificarEntrega());

        menu.run();
    }

    /**
     *  Estado -
     */
    private void gestaoDeOpcoes() {
        Menu menu = new Menu(new String[]{
                "Povoar base de dados (adicionar paletes e robot)",
                "Adicionar Robot",
                "Eliminar Robot",
                "etc"
        });

        // Registar os handlers
        //menu.setHandler(1, ()->criarArmazem());
        menu.setHandler(2, ()->povoarBD());
        menu.setHandler(3, ()->adicionarRobot());
        menu.setHandler(4, ()->eliminarRobot());

        menu.run();
    }


    /**
     *  Comunicar qrcode
     */
    private void comunicarQRCode() {
        try {
            System.out.println("Inserir QRCode: ");
            String qr = scin.nextLine();                //Exemplo-> 1;1:1;materia1;1.1;1
                                                        //idpalete;pesopalete:idmateria;nomemateria;pesomateria;quatidademateria

            //criar objeto qrcode
            QRCode qrcode = new QRCode(qr);
            //chamar metodo criarPalete
            Palete p = qrcode.criarPalete();
            p.setLocalizacao(new Localizacao("ZRececao"));
            //adicionar paelte a zona de rececao
            this.model.adicionarPalete(p);

        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  comunicar ordem de transporte
     */
    private void comunicarOrdemTransporte() {
        try {
            System.out.println("Número da palete a transportar: ");
            String id = scin.nextLine();
            Palete p = this.model.getPalete(id);
            if (p!=null) {   //<- se nao existe palete sair
                System.out.println("Destino da palete: ");
                String destino = scin.nextLine();

                if (this.model.validaLocal(destino)) {  //<- valida se o destino e valido
                    if (!p.getLocalizacao().getLocal().equals(destino)) {  //<- se a origem for igual ao destino nao transportar
                        Robot robotdisponivel = this.model.getRobotDisponivel();
                        if(robotdisponivel!=null){
                            //chamar comunicarTransporte para criar percurso e enviar para o robot
                            this.model.comunicarTransporte(robotdisponivel,p,new Localizacao(destino));

                        }else{
                            //else guardar numa lista de ordens ?
                            System.out.println("Não há robot disponivel. Queue de ordens indisponivel.");
                        }
                    }else{
                        System.out.println("O destino da palete é igual a origem");
                    }
                }else{
                    System.out.println("O destino não existe");
                }
            } else {
                System.out.println("Essa palete não existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Consultas das paletes
     */
    public void consultarTudo(){
        try {
            for(Palete p:this.model.getPaletes()){
                System.out.println(p.toString());
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarZonaRececao(){
        try {
            for(Palete p:this.model.getPaletesZR()){
                System.out.println(p.toString());
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarZonaArmazenamento(){
        try {
            for(Palete p:this.model.getPaletesZA()){
                System.out.println(p.toString());
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarZonaEntrega(){
        try {
            for(Palete p:this.model.getPaletesZE()){
                System.out.println(p.toString());
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarTransportar(){
        try {
            for(Palete p:this.model.getPaletesTrans()){
                System.out.println(p.toString());
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * listar ordens dos robots
     */
    public void listarOrdens(){
        try {
            //vai a cada robot com estado diferente de "A espera" e indica o id do robot, o estado,.. (e a localizacao?)
            for(Robot r:this.model.getRobotComOrdens()){
                System.out.println(r.toStringOrdens());
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * notificar recolha
     */
    public void notificarRecolha(){
        try {
            System.out.println("Id do robot: ");
            String id = scin.nextLine();

            Robot r = this.model.getRobot(id);
            if(r==null){
                System.out.println("Robot com esse id não existe");
            }
            else if (!r.getEstado().equals("BUSCAR")){
                System.out.println("Robot com esse id não está a recolher palete");
            }
            else{
                r.setLocalizacao(new Localizacao(r.getPercurso().getcRecolhaFim()));
                String idP = r.getPalete().getId();
                Palete p = this.model.getPalete(idP);
                if (p.getLocalizacao()==null) {
                    r.setPalete(null);
                    r.setLocalizacao(new Localizacao("ZRobots"));
                    r.setEstado("LIVRE");
                }
                else {
                    p.setLocalizacao(null);
                    this.model.adicionarPalete(p);

                    r.setEstado("TRANSPORTAR");
                }
                this.model.addRobot(r);
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * notificar recolha
     */
    public void notificarEntrega(){
        try {
            //alterar localizacao do robot
            //verificar se nao ha palete no sitio -> entregar a palete -> colocar no local e retirar do robot
            //alterar estado do robot

            //verificar se há entregas na fila de espera
            //      -> Se sim, dar a ordem ao robot
            //      -> Se nao, alterar a localizacao do robot para a zona de descanso

            System.out.println("Id do robot: ");
            String id = scin.nextLine();

            Robot r = this.model.getRobot(id);
            if(r==null){
                System.out.println("Robot com esse id não existe");
            }
            else if (!r.getEstado().equals("TRANSPORTAR")){
                System.out.println("Robot com esse id não está a transportar palete");
            }
            else{
                r.setLocalizacao(new Localizacao(r.getPercurso().getcEntregaFim()));
                String idP = r.getPalete().getId();
                Palete p = this.model.getPalete(idP);

                //verificar que não ha palete naquele sitio...
                p.setLocalizacao(r.getLocalizacao());
                r.setEstado("REGRESSAR");

                this.model.adicionarPalete(p);

                //verificar se há ordens na lista de ordens
                r.setLocalizacao(new Localizacao(r.getPercurso().getcRobotsFim()));
                r.setEstado("LIVRE");
                r.setPercurso(new Percurso(r.getId(),null,null,null));
                r.setPalete(null);
                this.model.addRobot(r);
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void criarArmazem(){
        try {
            //adicionar localizacoes this.model.addLocalizacoes(String[] ...)
            String[] s = new String[]{"ZRececao", "C1", "P1N", "P2N", "P3N", "P4N", "CP1N",
                    "CP2N", "CP3N", "CP4N", "C2", "ZRobots", "CRobots", "CZEntrega", "ZEntrega", "C3",
                    "P1S", "P2S", "P3S", "P4S", "CP1S", "CP2S", "CP3S", "CP4S", "C4"};
            this.model.putAllLocalizacoes(s);
            //adicionar arestas?
            //atualizar armazem?
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void povoarBD() {
        try {
            //adicionar algumas paletes (e 1 robot?)
            Percurso per = new Percurso("1",null,null,null);
            Robot r = new Robot("1",Robot.Estado.LIVRE,null,per,new Localizacao("ZRobots"));
            if(this.model.getRobot("1")==null)this.model.addRobot(r);

            per = new Percurso("2",null,null,null);
            r = new Robot("2",Robot.Estado.LIVRE, null,per,new Localizacao("ZRobots"));
            if(this.model.getRobot("2")==null)this.model.addRobot(r);

            per = new Percurso("3",null,null,null);
            r = new Robot("3",Robot.Estado.LIVRE, null,per,new Localizacao("ZRobots"));
            if(this.model.getRobot("3")==null)this.model.addRobot(r);

            QRCode qrcode = new QRCode("1;1:1;materia1;1.1;1");
            Palete p = qrcode.criarPalete();
            p.setLocalizacao(new Localizacao("ZRececao"));
            if(this.model.getPalete("1")==null)this.model.adicionarPalete(p);

            qrcode = new QRCode("2;1:2;materia2;1.1;1");
            p = qrcode.criarPalete();
            p.setLocalizacao(new Localizacao("ZRececao"));
            if(this.model.getPalete("2")==null)this.model.adicionarPalete(p);

            qrcode = new QRCode("3;2.1:3;materia3;2.2;3");
            p = qrcode.criarPalete();
            p.setLocalizacao(new Localizacao("P3S"));
            if(this.model.getPalete("3")==null)this.model.adicionarPalete(p);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void adicionarRobot() {
        try {
            //id automatico ou manual? <- diferença se há stdin
            System.out.println("Id do robot a adicionar: ");
            String id = scin.nextLine();
            if(this.model.getRobot(id)==null){
                //adicionar robot com id lido
                Robot r = new Robot(id, Robot.Estado.LIVRE,null,new Percurso(id,null,null,null),new Localizacao("ZRobots"));

                //System.out.println(r.toString());
                System.out.println(r.getId());
                this.model.addRobot(r);
            }
            else{
                System.out.println("Robot com esse id já existe");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarRobot() {
        try {
            System.out.println("Id do robot a eliminar: ");
            String id = scin.nextLine();
            Robot r =this.model.getRobot(id);
            if(r==null){
                System.out.println("Robot com esse id não existe");
            }
            else if (!r.getEstado().equals("LIVRE")){
                System.out.println("Robot com esse id está ocupado");
            }
            else this.model.deleteRobot(id);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}