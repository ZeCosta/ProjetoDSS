package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Gestao.MateriaPrima;
import uminho.dss.turmas3l.business.Gestao.Palete;
import uminho.dss.turmas3l.business.Localizacao;
import uminho.dss.turmas3l.business.Sala;
import uminho.dss.turmas3l.business.Transporte.Percurso;
import uminho.dss.turmas3l.business.Transporte.Robot;
import uminho.dss.turmas3l.business.Turma;

import java.sql.*;
import java.util.Map;
import java.util.Set;

public class RobotDAO implements Map<String, Robot> {
    private static RobotDAO singleton = null;

    private RobotDAO(){
        try(Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS localizacao (" +
                    "id varchar(10) NOT NULL PRIMARY KEY)," +
                    "local varchar(20) NOT NULL)";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS materiaprima (" +
                    "id varchar(10) NOT NULL PRIMARY KEY," +
                    "nome varchar(45) DEFAULT NULL," +
                    "peso double(4,2) DEFAULT 0," +
                    "quantidade int(4) DEFAULT 0)";
            stm.executeUpdate(sql);

            /* Por fazer sql = "CREATE IF NOT EXISTS percurso (" +
                    "" */

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
                    "paleteId varchar(10), foreign key(paleteId) references paletesDAO(id)" +
                    "percursoId varchar(10), foreign key(percursoId) references percursoDAO(id))";  // Assume-se uma relação 1-n entre Turma e Aluno
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
                                        l = new Localizacao(rsc.getString("local"));
                                    }
                                }
                                p = new Palete(idPalete, rsa.getDouble("peso"),mp,l);
                            }
                        }
                }
                Percurso per = null;
                String percursoId = rs.getString("percursoId");
                /* TRATAR DO PERCURSO */

                r = new Robot(idRobot, e, )
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        */
        return r;
    }

    @Override
    public Robot put(String s, Robot robot) {
        return null;
    }

    @Override
    public Robot remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Robot> map) {

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
        return null;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        return null;
    }

    /**
     * Verifica se um robot existe na base de dados
     *
     * @param value robot procurado na tabela
     * @return true se o robot existe
     * @throws
     */
    /*@Override
    public boolean containsValue(Object value) {
        Robot r = (Robot) value;
        String idRobot = r.getId();
        return (this.containsKey(idRobot) && ;
    }*/
}
