import java.sql.*;

public final class Connections{
    /**
    Class, dedicated to database connection methods
     */

    private final static String password = "";
    private Connections(){}

    public static void loadDriver() {
        try{
            Class.forName(Utility.DRIVER_CLASS_PATH);
        }
        catch (ClassNotFoundException e) {
            System.err.println(Utility.ERR_CLASS_NOT_FOUND);
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(Utility.CONNECTION_URL, Utility.USERNAME, password);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_CANNOT_CONNECT);
            return null;
        }

        System.out.println(Utility.MSG_CONNECTED_SUCCESS);
        return connection;
    }
}