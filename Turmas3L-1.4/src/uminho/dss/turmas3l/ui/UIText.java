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
    private ITurmasFacade model;

    // Scanner para leitura
    private Scanner scin;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public UIText() {

        this.model = new TurmasFacade();
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
        menu.setPreCondition(3, ()->this.model.haAlunos() && this.model.haTurmas());

        // Registar os handlers
        menu.setHandler(1, ()->gestaoDeTurmas());
        // Falta handler para opção 2 - "Operações sobre Turmas"
        menu.setHandler(2,()->gestaoDeTurmas());
        menu.setHandler(3, ()->adicionarAlunoATurma());
        menu.setHandler(4, ()->gestaoRobots());

        menu.run();
    }

    /**
     *  Estado - Gestão de Alunos
     */
    private void gestaoRobots() {
        Menu menu = new Menu(new String[]{
                "Listar ordens de transporte",
                "Notificar entrega de palete",
                "Notificar recolha de palete"
        });

        // Registar os handlers
        menu.setHandler(1, ()->adicionarAluno());
        menu.setHandler(2, ()->consultarAluno());
        menu.setHandler(3, ()->listarAlunos());

        menu.run();
    }

    /**
     *  Estado - Adicionar Aluno
     */
    private void adicionarAluno() {
        try {
            System.out.println("Número da novo aluno: ");
            String num = scin.nextLine();
            if (!this.model.existeAluno(num)) {
                System.out.println("Nome da novo aluno: ");
                String nome = scin.nextLine();
                System.out.println("Email da novo aluno: ");
                String email = scin.nextLine();
                this.model.adicionaAluno(new Aluno(num, nome, email));
                System.out.println("Aluno adicionado");
            } else {
                System.out.println("Esse número de aluno já existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Consultar Aluno
     */
    private void consultarAluno() {
        try {
            System.out.println("Número a consultar: ");
            String num = scin.nextLine();
            if (this.model.existeAluno(num)) {
                System.out.println(this.model.procuraAluno(num).toString());
            } else {
                System.out.println("Esse número de aluno não existe!");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Listar Alunos
     */
    private void listarAlunos() {
        try {
            System.out.println(this.model.getAlunos().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * cenascoisoetalput
     */
    public void gestaoDeTurmas(){
        Menu menu = new Menu(new String[]{
                "Adicionar Turma",
                "Mudar Sala à Turma",
                "Listar Turmas"
        });

        // Registar os handlers
        menu.setHandler(1, ()->adicionarTurma());
        menu.setHandler(2, ()->mudarSalaATurma());
        menu.setHandler(3, ()->listarTurmas());

        menu.run();

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