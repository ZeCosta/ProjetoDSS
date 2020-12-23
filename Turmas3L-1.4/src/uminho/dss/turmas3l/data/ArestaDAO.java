/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Aresta;
import uminho.dss.turmas3l.business.Gestao.MateriaPrima;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Versão incompleta de um DAO para Turmas
 *
 * Tabelas a criar na BD: ver método getInstance
 *
 * @author JFC
 * @version 20201208
 */
public class ArestaDAO implements Map<String, Aresta> {
    private static ArestaDAO singleton = null;

    private ArestaDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS localizacao (" +
                    "id varchar(10) NOT NULL PRIMARY KEY)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS aresta (" +
                    "id varchar(20) NOT NULL PRIMARY KEY," +
                    "v1 varchar(45) NOT NULL," +
                    "v2 varchar(45) NOT NULL," +
                    "foreign key(v1) references localizacao(id)," +
                    "foreign key(v2) references localizacao(id))";
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
    public static ArestaDAO getInstance() {
        if (ArestaDAO.singleton == null) {
            ArestaDAO.singleton = new ArestaDAO();
        }
        return ArestaDAO.singleton;
    }

    /**
     * @return número de turmas na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM aresta")) {
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
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM aresta WHERE id='"+key.toString()+"'")) {
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
     * Esta implementação é provisória. Devia testar o objecto e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    /**
     * Obter uma palete, dado o seu id
     *
     * @param key id da palete
     * @return a palete caso exista (null noutro caso)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Aresta get(Object key) {
        Aresta a = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM aresta WHERE Id='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela

                // Reconstruir v1
                Localizacao l1 = null;
                String sql = "SELECT * FROM localizacao WHERE id='"+rs.getString("v1")+"'";
                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a sala
                        l1 = new Localizacao(rs.getString("id"));
                    } else {
                        // BD inconsistente!! Sala não existe - tratar com excepções.
                    } // catch é feito no try inicial - este try serve para fechar o ResultSet automaticamente
                    // Nota: abrir um novo ResultSet no mesmo Statement fecha o ResultSet anterior
                }

                // Reconstruir v2
                Localizacao l2 = null;
                sql = "SELECT * FROM localizacao WHERE id='"+rs.getString("v2")+"'";
                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a sala
                        l2 = new Localizacao(rs.getString("id"));
                    } else {
                        // BD inconsistente!! Sala não existe - tratar com excepções.
                    } // catch é feito no try inicial - este try serve para fechar o ResultSet automaticamente
                    // Nota: abrir um novo ResultSet no mesmo Statement fecha o ResultSet anterior
                }

                // Reconstruir aresta
                a = new Aresta(rs.getString("id"),l1,l2);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }



    /**
     * @param key o id da palete
     * @param a a palete
     * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Aresta put(String key, Aresta a) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a aresta
            //System.out.println(a.getV1().getLocal()+"->"+a.getV2().getLocal());
            stm.executeUpdate(
                    "INSERT INTO aresta VALUES ('"+key+"'," +
                            "'" +a.getV1().getLocal() +"','" +
                            a.getV2().getLocal()+"') " +
                            "ON DUPLICATE KEY UPDATE v1=VALUES(v1),"+
                            "v2=VALUES(v2)");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }


        return null;
    }

    /**
     * Remover uma palete, dado o seu id
     *
     * NOTA: Não estamos a apagar a localizacao, mas estamos a apagar a materia...
     *
     * @param key id da palete a remover
     * @return a palete removida
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Aresta remove(Object key) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar a aresta
            stm.executeUpdate("DELETE FROM aresta WHERE Id='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }



    /**
     * Adicionar um conjunto de palete à base de dados
     *
     * @param arestas as paletes a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends String, ? extends Aresta> arestas) {
        for(Aresta a : arestas.values()) {
            this.put(a.getId(), a);
        }
    }

    /**
     * Apagar todas as paletes -> não achamos necessaria a implementação
     *
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
                              Statement stm = conn.createStatement()) {
            /*stm.execute("UPDATE alunos SET Turma=NULL");
            stm.executeUpdate("TRUNCATE turmas");*/
        stm.executeUpdate("DELETE FROM aresta");
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
     * @return Todas as paletes da base de dados
     */
    @Override
    public Collection<Aresta> values() {
        Collection<Aresta> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM aresta")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("Id"); // Obtemos um id de turma do ResultSet
                Aresta a = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(a);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<Entry<String, Aresta>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aresta>> entrySet() not implemented!");
    }
}
