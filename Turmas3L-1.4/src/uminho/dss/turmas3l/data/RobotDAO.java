package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Gestao.MateriaPrima;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;
import uminho.dss.turmas3l.business.Sala;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;
import uminho.dss.turmas3l.business.Turma;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RobotDAO implements Map<String, Robot> {
    private static RobotDAO singleton = null;

    private RobotDAO(){
        try(Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS localizacao (" +
                    "id varchar(10) NOT NULL PRIMARY KEY)";
            stm.executeUpdate(sql);
            /*
            sql = "CREATE TABLE IF NOT EXISTS materiaprima (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "nome varchar(45) DEFAULT NULL," +
                    "peso double(4,2) DEFAULT 0," +
                    "quantidade int(4) DEFAULT 0)";
            stm.executeUpdate(sql);
            */
            sql = "CREATE TABLE IF NOT EXISTS percurso (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "cRecolha varchar(45) DEFAULT NULL," +
                    "cEntrega varchar(45) DEFAULT NULL," +
                    "cRobots varchar(45) DEFAULT NULL)";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS palete (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "peso double(6,2) DEFAULT NULL," +
                    "localizacao varchar(10)," +
                    "materia varchar(10)," +
                    "foreign key(localizacao) references localizacao(id),"+
                    "foreign key(materia) references materiaprima(id))";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS robot (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "estado varchar(15) DEFAULT NULL," +
                    "localizacao varchar(10) ," +
                    "palete varchar(10) DEFAULT NULL," +
                    "percurso varchar(10), " +
                    "foreign key(localizacao) references localizacao(id),"+
                    "foreign key(palete) references palete(id)," +
                    "foreign key(percurso) references percurso(id))";
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
    public static RobotDAO getInstance() {
        if (RobotDAO.singleton == null) {
            RobotDAO.singleton = new RobotDAO();
        }
        return RobotDAO.singleton;
    }

    /**
     * @return número de robots na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM robot")) {
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
     * Método que verifica se existem robots no armazém
     *
     * @return true se não existir nenhum
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de robot existe na base de dados
     *
     * @param key id do robot
     * @return true se o id do robot existe
     * @throws
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM robot WHERE id='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    /**
     * Obter um robot, dado o seu id
     *
     * @param key id do robot
     * @return o robot caso exista (null noutro caso)
     * @throws
     */
    @Override
    public Robot get(Object key) {
        Robot r = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM robot WHERE id='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Verificar se o robot possui uma palete com ele
                Palete p = null;
                MateriaPrima mp = null;
                Localizacao l = null;
                String idRobot = rs.getString("id");
                String estado = rs.getString("estado");
                Robot.Estado e = Robot.Estado.valueOf(estado);
                if(e.equals(Robot.Estado.TRANSPORTAR)){
                        // Robot possui palete com ele, é preciso reconstruir a localização da palete, a matéria prima e a própria palete
                        String idPalete = rs.getString("paleteId");
                        try(ResultSet rsa = stm.executeQuery("SELECT * FROM palete WHERE id='"+idPalete+"'")){
                            //Reconstruir matéria prima
                            if(rsa.next()){
                                String idMateria = rsa.getString("materia");
                                try(ResultSet rsb = stm.executeQuery("SELECT * FROM materiaprima WHERE id='"+idMateria+"'")){
                                    if(rsb.next()){
                                        mp = new MateriaPrima(rsb.getString("id"),
                                                rsb.getString("nome"),
                                                rsb.getDouble("peso"),
                                                rsb.getInt("quantidade"));
                                    }
                                }
                                String idLocalizacao = rsa.getString("localizacao");
                                try(ResultSet rsc = stm.executeQuery("SELECT * FROM localizacao WHERE id='"+idLocalizacao+"'")){
                                    if(rsc.next()){
                                        l = new Localizacao(rsc.getString("l_local"));
                                    }
                                }
                                p = new Palete(idPalete, rsa.getDouble("peso"),mp,l);
                            }
                        }
                }
                Percurso per = null;
                String percursoId = rs.getString("percursoId");
                try(ResultSet rsa = stm.executeQuery("SELECT * FROM percurso WHERE id='"+percursoId+"'")){
                    if(rsa.next()){
                        per = new Percurso(rsa.getString("id"),
                                rsa.getString("cRecolha"),
                                rsa.getString("cEntrega"),
                                rsa.getString("cRobots"));
                    }
                }

                r = new Robot(idRobot, e, p, per, l);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return r;
    }

    @Override
    public Robot put(String id, Robot r) {
        Robot res = null;
        Localizacao l = r.getLocalizacao();
        Percurso per = r.getPercurso();
        Palete p = r.getPalete();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {


            // adicionar localizacao se nao existe
            stm.executeUpdate(
                    "INSERT IGNORE INTO localizacao " +
                            "VALUES ('"+ l.getLocal()+ "') ");

            // Atualizar percurso
            stm.executeUpdate(
                    "INSERT INTO percurso " +
                            "VALUES ('"+ per.getId()+ "', '"+
                            per.getcRecolha()+"', '"+
                            per.getcEntrega()+"', '"+
                            per.getcRobots()+"') " +
                            "ON DUPLICATE KEY UPDATE cRecolha=Values(cRecolha), " +
                            "cEntrega=Values(cEntrega), " +
                            "cRobots=Values(cRobots)");
            /*
            MateriaPrima m = p.getMateriaPrima();
            Localizacao lPalete = p.getLocalizacao();
            //Atualizar a materia prima
            stm.executeUpdate(
                    "INSERT INTO materiaprima VALUES ('"+m.getId()+"', "+m.getNome()+
                            ", '"+m.getPeso()+"', '" +
                            m.getQtd()+"') " +
                            "ON DUPLICATE KEY UPDATE nome=VALUES(nome),"+
                            "quantidade=VALUES(quantidade),"+
                            "peso=VALUES(peso)");

            // Atualizar a palete
            stm.executeUpdate(
                    "INSERT INTO palete VALUES ('"+p.getId()+"', "+p.getPeso()+
                            ", '"+l.getLocal()+"', '" +
                            m.getId()+"') " +
                            "ON DUPLICATE KEY UPDATE localizacao=VALUES(localizacao),"+
                            "materia=VALUES(materia),"+
                            "peso=VALUES(peso)");
            */



            if(r.getPalete()!=null){
                stm.executeUpdate(
                        "INSERT INTO robot " +
                                "VALUES ('"+ r.getId()+ "', '"+
                                r.getEstado()+"', '"+
                                r.getLocalizacao().getLocal()+"', '"+
                                r.getPalete().getId()+"', '"+
                                r.getPercurso().getId()+"') " +
                                "ON DUPLICATE KEY UPDATE estado=Values(estado), " +
                                "localizacao=Values(localizacao), " +
                                "percurso=Values(percurso), " +
                                "palete=Values(palete)");
            }else{
                stm.executeUpdate(
                        "INSERT INTO robot " +
                                "VALUES ('"+ r.getId()+ "', '"+
                                r.getEstado()+"', '"+
                                r.getLocalizacao().getLocal()+"', "+
                                "NULL"+", '"+
                                r.getPercurso().getId()+"') " +
                                "ON DUPLICATE KEY UPDATE estado=Values(estado), " +
                                "localizacao=Values(localizacao), " +
                                "percurso=Values(percurso), " +
                                "palete=Values(palete)");

            }


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public Robot remove(Object key) {
        Robot r = this.get(key);
        Palete p = r.getPalete();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar o percurso
            stm.executeUpdate("DELETE FROM percurso WHERE id='"+r.getPercurso().getId()+"'");

            //apagar o robot
            stm.executeUpdate("DELETE FROM robot WHERE id='"+r.getId()+"'");

        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Robot> robots) {
        for(Robot r : robots.values()) {
            this.put(r.getId(), r);
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Robot> values() {
        Collection<Robot> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM robot")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("id"); // Obtemos um id de turma do ResultSet
                Robot r = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(r);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        return null;
    }
}
