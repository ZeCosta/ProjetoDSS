/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Aluno;
import uminho.dss.turmas3l.business.Sala;
import uminho.dss.turmas3l.business.Turma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Versão incompleta de um DAO para Turmas
 *
 * Tabelas a criar na BD: ver método getInstance
 *
 * @author JFC
 * @version 20201208
 */
public class TurmaDAO implements Map<String, Turma> {
    private static TurmaDAO singleton = null;

    private static final String USERNAME = "dss"; //TODO: alterar
    private static final String PASSWORD = "dss"; //TODO: alterar
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/turmas3l";


    private TurmaDAO() {
//        Driver é carregado automaticamente quando se abre uma conexão
//        try {
//            Class.forName("org.mariadb.jdbc.Driver");
//        }
//        catch (ClassNotFoundException e) {
//            // Driver não disponível
//            e.printStackTrace();
//            throw new NullPointerException(e.getMessage());
//        }
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS salas (" +
                    "Num varchar(10) NOT NULL PRIMARY KEY," +
                    "Edificio varchar(45) DEFAULT NULL," +
                    "Capacidade int(4) DEFAULT 0)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS turmas (" +
                    "Id varchar(10) NOT NULL PRIMARY KEY," +
                    "Sala varchar(10) DEFAULT NULL," +
                    "foreign key(Sala) references salas(Num))";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS alunos (" +
                    "Num varchar(10) NOT NULL PRIMARY KEY," +
                    "Nome varchar(45) DEFAULT NULL," +
                    "Email varchar(45) DEFAULT NULL," +
                    "Turma varchar(10), foreign key(Turma) references turmas(Id))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static TurmaDAO getInstance() {
        if (TurmaDAO.singleton == null) {
            TurmaDAO.singleton = new TurmaDAO();
        }
        return TurmaDAO.singleton;
    }

    /**
     * @return número de turmas na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM turmas")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    /**
     * Método que verifica se existem turmas
     *
     * @return true se existirem 0 turmas
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que cerifica se um id de turma existe na base de dados
     *
     * @param key id da turma
     * @return true se a turma existe
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Id FROM turmas WHERE Id='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se uma turma existe na base de dados
     *
     * Esta implementação é provisória. Devia testar todo o objecto e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsValue(Object value) {
        Aluno a = (Aluno) value;
        return this.containsKey(a.getNumero());
    }

    /**
     * Obter uma turma, dado o seu id
     *
     * @param key id da turma
     * @return a turma cao exista (null noutro caso)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Turma get(Object key) {
        Turma t = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM turmas WHERE Id='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a colecção de alunos da turma - por implementar
                Collection<Aluno> alunos = new TreeSet<>(Aluno.NumComparator);
                // ...

                // Reconstruir a Sala
                Sala s = null;
                String sql = "SELECT * FROM salas WHERE Num='"+rs.getString("Sala")+"'";
                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a sala
                        s = new Sala(rs.getString("Sala"),
                                     rsa.getString("Edificio"),
                                     rsa.getInt("Capacidade"));
                    } else {
                        // BD inconsistente!! Sala não existe - tratar com excepções.
                    }
                }   // catch é feito no try inicial - este try serve para fechar o ResultSet automaticamente

                // Reconstruir a turma cokm os dados obtidos da BD 
                t = new Turma(rs.getString("Id"), s, alunos);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Insere uma turma na base de dados
     *
     * ATENÇÂO: Esta implementação é provisória.
     * Falta devolver o valor existente (caso exista um)
     * Falta remover a sala anterior, caso esteja a ser alterada
     * Falta guardar os alunos da turma na base de dados (ver Ficha 5)
     * Deviaria utilizar transacções...
     *
     * @param key o id da turma
     * @param t a turma
     * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Turma put(String key, Turma t) {
        Turma res = null;
        Sala s = t.getSala();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {

            // Actualizar a Sala
            stm.executeUpdate(
                    "INSERT INTO salas " +
                                "VALUES ('"+ s.getNumero()+ "', '"+
                                            s.getEdificio()+"', "+
                                            s.getCapacidade()+") " +
                                "ON DUPLICATE KEY UPDATE Edificio=Values(Edificio), " +
                                                        "Capacidade=Values(Capacidade)");

            // Actualizar a turma
            stm.executeUpdate(
                    "INSERT INTO turmas VALUES ('"+t.getId()+"', '"+s.getNumero()+"') " +
                                "ON DUPLICATE KEY UPDATE Sala=VALUES(Sala)");

            // Actualizar os alunos da turma
            // Por implementar

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    /**
     * Remover uma turma, dado o seu id
     *
     * NOTA: Não estamos a apagar a sala...
     *
     * @param key id da turma a remover
     * @return a turma removida
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Turma remove(Object key) {
        Turma t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM turmas WHERE Id='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adicionar um conjunto de turmas à base de dados
     *
     * @param turmas as turmas a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends String, ? extends Turma> turmas) {
        for(Turma t : turmas.values()) {
            this.put(t.getId(), t);
        }
    }

    /**
     * Apagar todos as turmas
     *
     * @throws NullPointerException ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE turmas");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    /**
     * @return Todos as turmas da base de dados
     */
    @Override
    public Collection<Turma> values() {
        Collection<Turma> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM turmas")) {
            while (rs.next()) {   // Utilizamos o get para construir as turmas
                col.add(this.get(rs.getString("Id")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<Entry<String, Turma>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
}
