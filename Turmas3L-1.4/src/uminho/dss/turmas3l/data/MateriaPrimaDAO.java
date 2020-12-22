package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Aluno;
import uminho.dss.turmas3l.business.Gestao.*;

import java.sql.*;
import java.util.*;

public class MateriaPrimaDAO implements Map<String, MateriaPrima> {
    private static MateriaPrimaDAO singleton = null;

    private MateriaPrimaDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS materiaprima (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "Nome varchar(45) DEFAULT NULL," +
                    "Peso double(5,2) DEFAULT 0)" +
                    "Quantidade int(4) DEFAULT 0)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static MateriaPrimaDAO getInstance() {
        if (MateriaPrimaDAO.singleton == null) {
            MateriaPrimaDAO.singleton = new MateriaPrimaDAO();
        }
        return MateriaPrimaDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM materiaprima")) {
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

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM materiaprima WHERE id='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        MateriaPrima mp = (MateriaPrima) value;
        return this.containsKey(mp.getId());
    }

    public MateriaPrima get(Object key) {
        MateriaPrima mp = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM materiaprima WHERE id='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir o aluno com os dados obtidos da BD - a chave estranjeira da turma, não é utilizada aqui.
                mp = new MateriaPrima(rs.getString("id"), rs.getString("Nome"), rs.getDouble("Peso"), rs.getInt("Qtd"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return mp;
    }

    public MateriaPrima put(String key, MateriaPrima mp) {
        MateriaPrima res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "INSERT INTO materiaprima VALUES ('"+mp.getId()+"', '"+mp.getNome()+"', '"+mp.getPeso()+"', '"+mp.getQtd()+"' NULL) " +
                            "ON DUPLICATE KEY UPDATE Nome=VALUES(Nome), Peso=VALUES(Email), Quantidade=VALUES(Quantidade)");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public MateriaPrima remove(Object key) {
        MateriaPrima mp = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM materiaprima WHERE id='"+key+"'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return mp;
    }

    public void putAll(Map<? extends String, ? extends MateriaPrima> mps) {
        for(MateriaPrima mp : mps.values()) {
            this.put(mp.getId(), mp);
        }
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE materiaprima");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    public Collection<MateriaPrima> values() {
        Collection<MateriaPrima> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM materiaprima")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    public Set<Entry<String, MateriaPrima>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,MateriaPrima>> entrySet() not implemented!");
    }
}
