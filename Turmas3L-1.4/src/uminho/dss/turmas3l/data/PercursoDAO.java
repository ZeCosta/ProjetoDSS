package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Gestao.MateriaPrima;
import uminho.dss.turmas3l.business.Transporte.*;

import java.sql.*;
import java.util.*;

public class PercursoDAO implements Map<String, Percurso> {
    private static PercursoDAO singleton = null;

    private PercursoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS percurso (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "cRecolha varchar(1000) DEFAULT NULL," +
                    "cEntrega varchar(1000) DEFAULT NULL)" +
                    "cRobots varchar(1000) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PercursoDAO getInstance() {
        if (PercursoDAO.singleton == null) {
            PercursoDAO.singleton = new PercursoDAO();
        }
        return PercursoDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM percurso")) {
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
                     stm.executeQuery("SELECT id FROM percurso WHERE id='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        Percurso p = (Percurso) value;
        return this.containsKey(p.getId());
    }

    public Percurso get(Object key) {
        Percurso p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM percurso WHERE id='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                p = new Percurso(rs.getString("Id"), rs.getString("cRecolha"), rs.getString("cEntrega"), rs.getString("cRobots"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    public Percurso put(String key, Percurso p) {
        Percurso res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "INSERT INTO percurso VALUES ('"+p.getId()+"', '"+p.getcEntrega()+"', '"+p.getcRecolha()+"', '"+p.getcRobots()+"' NULL) " +
                            "ON DUPLICATE KEY UPDATE cRecolha=VALUES(cRecolha), cEntrega=VALUES(cEntrega), cRobots=VALUES(cRobots)");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Percurso remove(Object key) {
        Percurso p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM percurso WHERE id='"+key+"'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    public void putAll(Map<? extends String, ? extends Percurso> ps) {
        for(Percurso p : ps.values()) {
            this.put(p.getId(), p);
        }
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE percurso");
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

    public Collection<Percurso> values() {
        Collection<Percurso> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM percurso")) {
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
    public Set<Entry<String, Percurso>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,MateriaPrima>> entrySet() not implemented!");
    }

}
