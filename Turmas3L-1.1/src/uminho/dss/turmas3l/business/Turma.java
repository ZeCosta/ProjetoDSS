/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.business;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Classe que representa uma turma
 *
 * ATENÇÃO:
 * Esta versão ainda utiliza um Collection para registar a lista de alunos da turma.
 * Essa colecção deverá passsar a ser apenas dos números dos alunos quando o exercício da Ficha 5 for resolvido.
 *
 * Considera-se que a lista de alunos é uma agregação.
 *
 * @author JFC
 * @version 20201206
 */
public class Turma {

    private String id;
    private Sala sala;
    private Collection<Aluno> lstalunos;

    public Turma() {
        this.id = "";
        this.sala = new Sala();
        this.lstalunos = new TreeSet<>(Aluno.NumComparator);
    }

    public Turma(String id, Sala sala) {
        this();
        this.sala = sala;
        this.id = id;
    }

    public Turma(String id, Sala sala, Collection<Aluno> alunos) {
        this();
        this.sala = sala;
        this.id = id;
        this.lstalunos.addAll(alunos);
    }

    /**
     * @return identificador da turma
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return sala da turma
     */
    public Sala getSala() { return sala; }

    /**
     * @param s nova sala da turma
     */
    public void setSala(Sala s) {
        this.sala = s;
    }
    /**
     * Método que adiciona um aluno à turma.
     *
     * Este método acaba por não ter efeito prático pois TurmaDAO não está a guardar os alunos na BD.
     * O método terá que ser alterado quando se acrescentar o AlunoDAO e lstalunos passar a ser
     * uma Collection de números de aluno.
     * Duas possibilidades:
     * 1. mater a assinatura do método e guardar a.getNumero() na colecção
     * 2. mudar a assinatura do método para apenas receber o número.
     * A primeira solução evita que se tenha que alterar a camada de interface com o utilizador.
     *
     * Em qualquer dos casos, deverá ser garantido que o aluno existe no AlunoDAO.
     *
     * @param a novo aluno
     */
    public void adiciona(Aluno a) {
        if (!existe(a.getNumero())) {
            lstalunos.add(a);
        } else {
            // Erro! - completar com excepções
        }
    }

    /**
     * @param num número de aluno a procurar
     * @return true se um aluno com este número existe
     */
    public boolean existe(String num) {
        return lstalunos.stream().anyMatch(a->a.getNumero().equals(num));
    }

    /**
     * @param num número de aluno a procurar
     * @return aluno com o número indicado, caso estista (null em caso contrário).
     */
    public Aluno procura(String num) {
        return lstalunos.stream().filter(a->a.getNumero().equals(num)).findFirst().orElse(null);
    }

    /**
     * @return Todos os alunos da turma
     */
    public Collection<Aluno> getAlunos() {
        Collection<Aluno> res = new TreeSet<>(Aluno.NumComparator);
        res.addAll(lstalunos);
        return res;
    }

    /**
     * @return String que representa a Turma
     */
    public String toString() {
        return "Turma("+this.id+", "+this.sala+", "+this.lstalunos +")";
    }

}
