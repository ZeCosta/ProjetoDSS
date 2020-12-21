/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.ui;

import uminho.dss.turmas3l.business.*;

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
            //chamar metodod criarPalete
            //adicionar paelte a zona de rececao

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
            if (this.model.existePalete(num)) {
                System.out.println("Destino da palete: ");
                String destino = scin.nextLine();

                //if (this.model.origem(num)) {  <- se a origem for igual ao destino nao transportar
                //else{System.out.println("O destino da palete é igual a origem");}
            } else {
                System.out.println("Essa palete não existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    public void adicionarTurma(){
        try {
            System.out.println("Número da nova turma: ");
            String num = scin.nextLine();
            if (!this.model.existeTurma(num)) {
                System.out.println("Número da sala da nova turma: ");
                String sala = scin.nextLine();

                //if (!this.model.existeSala(sala)) {
                System.out.println("Edificio da sala: ");
                String edificio = scin.nextLine();
                System.out.println("Capaciada da sala: ");
                String capacidade = scin.nextLine();
                this.model.adicionaTurma(new Turma(num,new Sala(sala,edificio,Integer.parseInt(capacidade))));
                System.out.println("Turma adicionada");
                //}
            } else {
                System.out.println("Esse número de aluno já existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void mudarSalaATurma(){
        try {
            System.out.println("Número da turma: ");
            String num = scin.nextLine();
            if (this.model.existeTurma(num)) {
                System.out.println("Número da sala da nova turma: ");
                String sala = scin.nextLine();

                //if (!this.model.existeSala(sala)) {
                System.out.println("Edificio da sala: ");
                String edificio = scin.nextLine();
                System.out.println("Capaciada da sala: ");
                String capacidade = scin.nextLine();
                this.model.adicionaTurma(new Turma(num,new Sala(sala,edificio,Integer.parseInt(capacidade))));
                System.out.println("Sala alterada");
                //}
            } else {
                System.out.println("Essa turma não existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarTurmas(){
        try {
            System.out.println(this.model.getTurmas().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     *  Estado - Adicionar Aluno a Turma
     */
    private void adicionarAlunoATurma() {
        try {
            System.out.println("Número da turma: ");
            String tid = scin.nextLine();
            if (this.model.existeTurma(tid)) {
                System.out.println("Número do aluno: ");
                String num = scin.nextLine();
                if (this.model.existeAluno(num)) {
                    this.model.adicionaAlunoTurma(tid, num);
                    System.out.println("Aluno adicionado à turma");
                } else {
                    System.out.println("Esse número de aluno não existe!");
                }
            } else {
                System.out.println("Esse número de turma não existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Remover Aluno de Turma
     */
    private void removerAlunoDeTurma() {
        try {
            System.out.println("Número da turma: ");
            String tid = scin.nextLine();
            if (this.model.existeTurma(tid)) {
                System.out.println("Número do aluno: ");
                String num = scin.nextLine();
                if (this.model.existeAlunoEmTurma(tid,num)) {
                    this.model.removeAlunoTurma(tid, num);
                    System.out.println("Aluno removido da turma");
                } else {
                    System.out.println("Esse número de aluno não existe na turma!");
                }
            } else {
                System.out.println("Esse número de turma não existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Listar Alunos da Turma
     */
    private void listarAlunosDaTurma() {
        try {
            System.out.println("Número da turma: ");
            String tid = scin.nextLine();
            System.out.println(this.model.getAlunos(tid).toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}