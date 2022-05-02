import java.sql.*;
import java.util.*;

public final class Query{
    /**
    Class, dedicated to query methods.
    */

    private Query(){}

    public static void commit(Connection connection){
        try{
            connection.commit();
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);

            try{
                connection.close();
            }
            catch(Exception ee){
                System.err.println(Utility.ERR_CANNOT_CLOSE_CONNECTION);
            }
        }
    }

    public static void rollback(Connection connection) {
        try{
            connection.rollback();
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);

            try{
                connection.close();
            }
            catch(Exception ee){
                System.err.println(Utility.ERR_CANNOT_CLOSE_CONNECTION);
            }
        }
    }

    public static ResultSet executeQuery(Connection connection, String query, Object[] params) throws SQLException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            castParams(statement, params);
            resultSet = statement.executeQuery();
        }
        catch (SQLException e) {
            throw e;
        }

        return resultSet;
    }

    public static void executeUpdateDelete(Connection connection, String query, Object[] params) throws SQLException{
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            castParams(statement, params);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public static void printAllRows(ResultSet set, int rowLimit, String title) throws SQLException{
        if(rowLimit == 0)
            return;
        
        if(rowLimit == -1)
            rowLimit = Integer.MAX_VALUE - 2;

        System.out.println(title);

        try {
            set.beforeFirst();
            int columns = set.getMetaData().getColumnCount();
            int rowNum = 1;

            int[] displaySizes = new int[columns];
            int size;
            int maxSize;
            int overallSize = 10 + (columns - 1) * 3;

            //Getting the appropriate sizes for each column
            for (int i = 1; i <= columns; ++i) {
                maxSize = 0;

                int titleSize = set.getMetaData().getColumnName(i).length();
                if(titleSize > maxSize)
                    maxSize = titleSize;

                while (set.next()) {
                    try{
                        size = set.getString(i).length();

                        if(size > maxSize)
                            maxSize = size;
                    }
                    catch(NullPointerException e){
                        continue;
                    }
                }

                displaySizes[i - 1] = maxSize;
                overallSize += maxSize;
                set.beforeFirst();
            }

            // Printing the header
            System.out.printf("%-10s", "[-] -> ");
            for (int i = 1; i <= columns; ++i){
                if(i > 1)
                    System.out.print(" | ");

                System.out.printf("%" + displaySizes[i - 1] + "s", set.getMetaData().getColumnName(i));
            }

            // Printing the delimiter
            String delims = new String(new char[overallSize]).replace('\0', '-');
            System.out.print("\n" + delims + "\n");

            // Printing the data
            while (set.next() && rowNum <= rowLimit) {
                System.out.printf("%-10s", "[" + rowNum + "] -> ");

                for (int i = 1; i <= columns; ++i){
                    if(i > 1)
                        System.out.print(" | ");

                    System.out.printf("%" + displaySizes[i - 1] + "s", set.getString(i));
                }

                System.out.print("\n");
                ++rowNum;
            }

            System.out.print("\n");
        }
        catch(SQLException e){
            throw e;
        }
    }

    public static String idSelection(Scanner in, ResultSet set, String selectionText) throws SQLException, NumberFormatException{
        while(true){
            printAllRows(set, -1, "");

            set.last();
            int size = set.getRow();
            set.beforeFirst();

            System.out.print(selectionText);
            String input = in.nextLine();

            if(input.toLowerCase().equals(Utility.CANCEL_STR))
                return input.toLowerCase();

            int num;

            try{
                num = Integer.parseInt(input);
            }
            catch (NumberFormatException e){
                System.err.println(Utility.ERR_WRONG_INPUT);
                continue;
            }

            if(num < 1 || num > size){
                System.err.println(Utility.ERR_WRONG_INPUT);
                continue;
            }

            try {
                //Setting the abosolute row to the current one and getting the id column
                set.absolute(num);
                return set.getString(1);
            }
            catch (SQLException e) {
                throw e;
            }
        }
    }

    public static String idInput(Scanner in, Connection connection, String selectionText, String tableName) throws SQLException{
        ResultSet set = executeQuery(connection, "SELECT * FROM " + Utility.USERNAME + "." + tableName, null);
        String input;

        while(true){
            System.out.print("\n");
            System.out.print(selectionText);
            input = in.nextLine();

            if(input.toLowerCase().equals(Utility.CANCEL_STR))
                return input.toLowerCase();

            try {
                boolean hasId = false;
                while(set.next()){
                    if(set.getString(1).equals(input)){
                        hasId = true;
                        break;
                    }
                }

                if(hasId){
                    System.err.println(Utility.ERR_ID_PRESENT);
                    continue;
                }

                break;
            }
            catch (SQLException e) {
                throw e;
            }
        }

        return input;
    }

    public static void castParams(PreparedStatement statement, Object[] params) throws SQLException{
        if(params == null)
            return;

        try {
            for (int i = 0; i < params.length; ++i) {
                if (params[i] instanceof Integer)
                    statement.setInt(i + 1, (Integer) params[i]);
                else if (params[i] instanceof java.sql.Date)
                    statement.setDate(i + 1, (java.sql.Date) params[i]);
                else if (params[i] instanceof java.sql.Time)
                    statement.setTime(i + 1, (java.sql.Time) params[i]);
                else if (params[i] instanceof Float)
                    statement.setDouble(i + 1, (float) params[i]);
                else
                    statement.setString(i + 1, (String) params[i]);
            }
        }
        catch (SQLException e) {
            throw e;
        }
    }
}