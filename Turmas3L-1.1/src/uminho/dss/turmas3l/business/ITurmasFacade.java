/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.business;

import java.util.Collection;
import java.util.Map;

/**
 * API da Facade da lógica de negócio.
 *
 * @author DSS
 * @version 20201206
 */
public interface ITurmasFacade {

    void adicionaAluno(Aluno a);

    void adicionaTurma(Turma a);

    void alteraSalaDeTurma(String tid, Sala s);

    boolean existeTurma(String tid);

    Aluno procuraAluno(String num);

    Collection<Aluno> getAlunos(String tid);

    Collection<Turma> getTurmas();

    boolean existeAluno(String num);

    void adicionaAlunoTurma(String tid, String num);

    Collection<Aluno> getAlunos();
}
