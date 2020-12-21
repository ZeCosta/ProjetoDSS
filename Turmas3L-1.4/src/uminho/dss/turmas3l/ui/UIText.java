/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.ui;

import uminho.dss.turmas3l.business.*;
import uminho.dss.turmas3l.business.Gestao.Palete;

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
        System.out.println("Bem vindo ao Sistema de Gestão de Turmas!");
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
                "Consultar Listagem",
                "Operações sobre robots"
        });

        // Registar pré-condições das transições
        //menu.setPreCondition(4, ()->this.model.haRobot() );

        // Registar os handlers
        menu.setHandler(1, ()->comunicarQRCode());
        menu.setHandler(2,()->comunicarOrdemTransporte());
        menu.setHandler(3, ()->consultarListagem());
        menu.setHandler(4, ()->gestaoRobots());

        menu.run();
    }

    /**
     *  Estado - Gestão de Alunos
     */
    private void consultarListagem() {
        Menu menu = new Menu(new String[]{
                "Consultar tudo",
                "Consultar zona de receção",
                "Consultar zona de armazenamento",
                "Consultar zona de entrega"
        });

        // Registar os handlers
        menu.setHandler(1, ()->consultarTudo());
        menu.setHandler(2, ()->consultarZonaRececao());
        menu.setHandler(3, ()->consultarZonaArmazenamento());
        menu.setHandler(3, ()->consultarZonaEntrega());

        menu.run();
    }

    /**
     *  Estado - Gestão de Alunos
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
     *  Comunicar qrcode
     */
    private void comunicarQRCode() {
        try {
            System.out.println("Inserir QRCode: ");
            String qr = scin.nextLine();

            //criar objeto qrcode
            QRCode qrcode = new QRCode(qr);
            //chamar metodod criarPalete
            Palete p = qrcode.criarPalete();
            //adicionar paelte a zona de rececao
            this.model.adicionarPaleteZR(p); //-> model adiciona ao DAO e zonaderececao altera a localizacao?

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
            String num = scin.nextLine();
            /*if (this.model.existePalete(num)) {   <- se nao existe palete sair
                System.out.println("Destino da palete: ");
                String destino = scin.nextLine();

                //if (this.model.origem(num)) {  <- se a origem for igual ao destino nao transportar
                //else{System.out.println("O destino da palete é igual a origem");}
            } else {
                System.out.println("Essa palete não existe!");
            }*/
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
            //System.out.println(this.model.getPaletesZR().toString());
            //System.out.println(this.model.getPaletesZA().toString());
            //System.out.println(this.model.getPaletesZE().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarZonaRececao(){
        try {
            //System.out.println(this.model.getPaletesZR().toString());
            //System.out.println(this.model.getPaletesZA().toString());
            //System.out.println(this.model.getPaletesZE().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarZonaArmazenamento(){
        try {
            //System.out.println(this.model.getPaletesZR().toString());
            //System.out.println(this.model.getPaletesZA().toString());
            //System.out.println(this.model.getPaletesZE().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void consultarZonaEntrega(){
        try {
            //System.out.println(this.model.getPaletesZR().toString());
            //System.out.println(this.model.getPaletesZA().toString());
            //System.out.println(this.model.getPaletesZE().toString());
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
            //System.out.println(this.model.getOrdens().toString()); <- vai a cada robot com estado diferente de "A espera" e indica o estado,.. (e a localizacao?)
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
            //alterar localizacao do robot
            //confirmar que a palete esta no sitio
            //recolher a palete -> colocar no robot e retirar do local
            //alterar estado do robot
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
            //entregar a palete -> colocar no local e retirar do robot
            //alterar estado do robot

            //verificar se há entregas na fila de espera
            //      -> Se sim, dar a ordem ao robot
            //      -> Se nao, alterar a localizacao do robot para a zona de descanso
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


}