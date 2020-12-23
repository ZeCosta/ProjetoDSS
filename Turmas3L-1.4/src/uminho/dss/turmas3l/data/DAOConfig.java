package uminho.dss.turmas3l.data;

public class DAOConfig {
    static final String USERNAME = "dss";
    static final String PASSWORD = "dss";
    private static final String DATABASE = "gestaoarmazem";
    //private static final String DRIVER = "jdbc:mariadb";        // Usar para MariaDB
    private static final String DRIVER = "jdbc:mysql";        // Usar para MySQL
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE;
}
