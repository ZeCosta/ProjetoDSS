package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Gestao.MateriaPrima;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;

import java.sql.*;
import java.util.*;

/**
 * DAO para Paletes
 */
public class PaleteDAO implements Map<String, Palete> {
    private static PaleteDAO singleton = null;

    private PaleteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS materiaprima (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "nome varchar(45) DEFAULT NULL," +
                    "peso double(4,2) DEFAULT 0," +
                    "quantidade int(4) DEFAULT 0)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS localizacao (" +
                    "id varchar(10) NOT NULL PRIMARY KEY)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS palete (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "peso double(6,2) DEFAULT NULL," +
                    "localizacao varchar(10)," +
                    "materia varchar(10)," +
                    "foreign key(localizacao) references localizacao(id),"+
                    "foreign key(materia) references materiaprima(id))";
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
    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }

    /**
     * @return número de paletes na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM palete")) {
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
     * Método que verifica se existem paletes
     *
     * @return true se existirem 0 paletes
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que cerifica se um id de palete existe na base de dados
     *
     * @param key id da palete
     * @return true se a palete existe
     * @throws NullPointerException
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM palete WHERE id='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se uma palete existe na base de dados
     *
     * @throws NullPointerException
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
     * @throws NullPointerException
     */
    @Override
    public Palete get(Object key) {
        Palete p = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM palete WHERE Id='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a MateriaPrima
                MateriaPrima m = null;
                String sql = "SELECT * FROM materiaprima WHERE id='"+rs.getString("materia")+"'";
                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a sala
                        m = new MateriaPrima(rs.getString("id"),
                                     rsa.getString("nome"),
                                rsa.getDouble("peso"),
                                rsa.getInt("quantidade"));
                    } else {
                        // BD inconsistente!! Sala não existe - tratar com excepções.
                    } // catch é feito no try inicial - este try serve para fechar o ResultSet automaticamente
                      // Nota: abrir um novo ResultSet no mesmo Statement fecha o ResultSet anterior
                }

                // Reconstruir a localizacao
                Localizacao l = null;
                sql = "SELECT * FROM localizacao WHERE id='"+rs.getString("localizacao")+"'";
                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a localizacao
                        l = new Localizacao(rsa.getString("id"));
                    } else {
                        // BD inconsistente!! Sala não existe - tratar com excepções.
                    } // catch é feito no try inicial - este try serve para fechar o ResultSet automaticamente
                    // Nota: abrir um novo ResultSet no mesmo Statement fecha o ResultSet anterior
                }

                // Reconstruir a palete cokm os dados obtidos da BD
                p = new Palete(rs.getString("id"),Double.parseDouble(rs.getString("peso")), m, l);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }



    /**
     * @param key o id da palete
     * @param p a palete
     * @return palete
     * @throws NullPointerException
     */
    @Override
    public Palete put(String key, Palete p) {
        Palete res = null;
        Localizacao l = p.getLocalizacao();
        MateriaPrima m = p.getMateriaPrima();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // adicionar localizacao se nao existe
            if(l!=null)stm.executeUpdate(
                    "INSERT IGNORE INTO localizacao " +
                                "VALUES ('"+ l.getLocal()+ "')");

            // Actualizar a MateriaPrima
            stm.executeUpdate(
                    "INSERT INTO materiaprima " +
                            "VALUES ('"+ m.getId()+ "', '"+
                            m.getNome()+"', "+
                            m.getPeso()+", "+
                            m.getQtd()+") " +
                            "ON DUPLICATE KEY UPDATE nome=Values(nome), " +
                            "peso=Values(peso), " +
                            "quantidade=Values(quantidade)");


            // Actualizar a palete
            if(l!=null)
            stm.executeUpdate(
                    "INSERT INTO palete VALUES ('"+p.getId()+"', "+p.getPeso()+
                            ", '"+l.getLocal()+"', '" +
                            m.getId()+"') " +
                                "ON DUPLICATE KEY UPDATE localizacao=VALUES(localizacao),"+
                                "materia=VALUES(materia),"+
                                "peso=VALUES(peso)");
            else stm.executeUpdate(
                    "INSERT INTO palete VALUES ('"+p.getId()+"', "+p.getPeso()+
                            ", NULL, '" +
                            m.getId()+"') " +
                            "ON DUPLICATE KEY UPDATE localizacao=VALUES(localizacao),"+
                            "materia=VALUES(materia),"+
                            "peso=VALUES(peso)");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    /**
     * Remover uma palete, dado o seu id
     *
     * NOTA: Não estamos a apagar a localizacao, mas estamos a apagar a materia...
     *
     * @param key id da palete a remover
     * @return a palete removida
     * @throws NullPointerException
     */
    @Override
    public Palete remove(Object key) {
        Palete p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar a materiaprima
            stm.executeUpdate("DELETE FROM materiaprima WHERE Id='"+p.getMateriaPrima().getId()+"'");

            // apagar a palete
            stm.executeUpdate("DELETE FROM palete WHERE Id='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }



    /**
     * Adicionar um conjunto de palete à base de dados
     *
     * @param paletes as paletes a adicionar
     * @throws NullPointerException
     */
    @Override
    public void putAll(Map<? extends String, ? extends Palete> paletes) {
        for(Palete p : paletes.values()) {
            this.put(p.getId(), p);
        }
    }

    /**
     * Apagar todas as paletes
     *
     * @throws NullPointerException
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            /*stm.execute("UPDATE alunos SET Turma=NULL");
            stm.executeUpdate("TRUNCATE turmas");*/
            stm.executeUpdate("DELETE FROM palete");
            stm.executeUpdate("DELETE FROM materiaprima");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * NÃO IMPLEMENTADO! -> Nao é necessaria
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
    public Collection<Palete> values() {
        Collection<Palete> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM palete")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("Id"); // Obtemos um id de turma do ResultSet
                Palete p = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(p);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    /**
     * NÃO IMPLEMENTADO! -> Nao é necessaria
     * @return ainda nada!
     */
    @Override
    public Set<Entry<String, Palete>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Palete>> entrySet() not implemented!");
    }
}
