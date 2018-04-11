package oldstyle.dbservices;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBService {
    private static DBService dbService;
    private static Connection connection;

    public static DBService instance() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if(dbService == null){
            dbService = new DBService();
        }
        return dbService;
    }

    private DBService() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());
    }

    public Connection getConnection() throws SQLException {
        if(connection == null) {
            String url = "jdbc:postgresql://localhost/nodedb";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "222");
//        props.setProperty("ssl","true");
            connection = DriverManager.getConnection(url, props);
        }
        return connection;
    }
}
