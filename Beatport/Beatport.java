import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Beatport {
    private static String localUsername;
    private static Scanner in;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //Initializing values
        Connections.loadDriver();
        in = new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat timeFormatLong = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);    
        SimpleDateFormat timeFormatShort = new SimpleDateFormat("mm:ss", Locale.ENGLISH);   

        //Connecting to the database
        Connection connection = Connections.getConnection();

        if(connection == null){
            System.err.println(Utility.ERR_CANNOT_CONNECT);
            return;
        }

        Utility.printWelcomeText();
        localUsername = Menu.inputUsername(in);

        if(connection != null){
            //Refreshing the record spotlight
            try{
                connection.setAutoCommit(false);
                Query.executeUpdateDelete(connection, SQLStatement.REFRESH_LABELS, null);
                Query.commit(connection);
            }
            catch(SQLException e){
                System.err.println(Utility.ERR_SQL);
                e.printStackTrace();
                Query.rollback(connection);
            }

            //Printing menu options
            while(true){
                if(connection == null)
                    break;

                //Determining the user's role
                if(localUsername.equals("ADMIN"))
                    Utility.printAdminMenu();
                else
                    Utility.printUserMenu();

                //Getting the command number and the arguments
                Object[] params;
                ArrayList<String> trimmedArgs;
                int selection;

                try{
                    params = Menu.inputArguments(in, true);
                    trimmedArgs = (ArrayList<String>)params[0];
                    selection = (int)params[1];
                }
                catch(NumberFormatException e){
                    System.err.println(Utility.ERR_INCORRECT_COMMAND);
                    continue;
                }

                /*ADMIN COMMANDS*/
                if(localUsername.equals("ADMIN")){

                    //Creating a new catalog
                    if(selection == Utility.AdminCommands.ADD_CATALOG.index) {
                        if (trimmedArgs.size() != 2 && trimmedArgs.size() != 3) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        String name = trimmedArgs.get(1);
                        String type = trimmedArgs.size() == 3 ? trimmedArgs.get(2) : "DEFAULT";

                        createCatalog(connection, name, type);
                    }

                    //Establishing a new label
                    else if(selection == Utility.AdminCommands.ESTABLISH_LABEL.index) {
                        if (trimmedArgs.size() != 3) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        String name = trimmedArgs.get(1);
                        String establishment_date_str = trimmedArgs.get(2);
                        Date establishment_date;

                        try{
                            establishment_date = new Date(dateFormat.parse(establishment_date_str).getTime());
                        }
                        catch (java.text.ParseException e){
                            System.err.println(Utility.ERR_WRONG_DATE);
                            continue;
                        }

                        establishLabel(connection, name, establishment_date);
                    }

                    //Preparing new record for release
                    else if(selection == Utility.AdminCommands.PREPARE_RECORD.index) {
                        if (trimmedArgs.size() != 7) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        String title = trimmedArgs.get(1);
                        String length_str = trimmedArgs.get(2);
                        String preorder_date_str = trimmedArgs.get(3);
                        String release_date_str = trimmedArgs.get(4);
                        String genre = trimmedArgs.get(5);
                        String price_str = trimmedArgs.get(6);

                        //Parsing dates
                        Date preorder_date;
                        Date release_date;

                        try{
                            preorder_date = new Date(dateFormat.parse(preorder_date_str).getTime());
                            release_date = new Date(dateFormat.parse(release_date_str).getTime());
                        }
                        catch (java.text.ParseException e){
                            System.err.println(Utility.ERR_WRONG_RECORD_DATES);
                            continue;
                        }

                        //Parsing time
                        Time length;

                        try{
                            long ms = timeFormatLong.parse(length_str).getTime();
                            length = new Time(ms);
                        }
                        catch (java.text.ParseException e){
                            try{
                                long ms = timeFormatShort.parse(length_str).getTime();
                                length = new Time(ms);
                            }
                            catch (java.text.ParseException ee){
                                System.err.println(Utility.ERR_WRONG_LENGTH);
                                continue;
                            }
                        }

                        //Parsing price
                        float price;

                        try{
                            price = Float.parseFloat(price_str);
                        }
                        catch (NumberFormatException e){
                            System.err.println(Utility.ERR_WRONG_PRICE);
                            continue;
                        }

                        prepareRecord(connection, title, length, preorder_date, release_date, genre, price);
                    }

                    //Registering a new artist
                    else if(selection == Utility.AdminCommands.REGISTER_ARTIST.index) {
                        if (trimmedArgs.size() != 5) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        String pseudonym = trimmedArgs.get(1);
                        String first_name = trimmedArgs.get(2);
                        String second_name = trimmedArgs.get(3);
                        String birth_date_str = trimmedArgs.get(4);

                        //Parsing date
                        Date birth_date;

                        try{
                            birth_date = new Date(dateFormat.parse(birth_date_str).getTime());
                        }
                        catch (java.text.ParseException e){
                            System.err.println(Utility.ERR_WRONG_DATE);
                            continue;
                        }

                        registerArtist(connection, pseudonym, first_name, second_name, birth_date);
                    }

                    else if(selection == Utility.AdminCommands.QUIT.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        return;
                    }

                    else
                        System.err.println(Utility.ERR_INCORRECT_COMMAND);
                }
                else{
                    /*USER COMMANDS*/

                    //Searching for Records by their title
                    if(selection == Utility.UserCommands.SEARCH_RECORD_TITLE.index) {
                        if (trimmedArgs.size() != 2) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        String title = trimmedArgs.get(1);
                        searchRecordsByTitle(connection, title);
                    }

                    //Searching for Artists by their pseudonym
                    else if(selection == Utility.UserCommands.SEARCH_ARTIST_PSEUDONYM.index) {
                        if (trimmedArgs.size() != 2) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        String pseudonym = trimmedArgs.get(1);
                        searchArtistsByPseudonym(connection, pseudonym);
                    }

                    //Preordering the record
                    else if(selection == Utility.UserCommands.PREORDER_RECORD.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        preorderRecord(connection);
                    }

                    //Cancelling the preorder
                    else if(selection == Utility.UserCommands.CANCEL_PREORDER.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        cancelPreorder(connection);
                    }

                    //Purchasing the record
                    else if(selection == Utility.UserCommands.PURCHASE_RECORD.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        purchaseRecord(connection);
                    }

                    
                    //Refunding the record purchase
                    else if(selection == Utility.UserCommands.REFUND_PURCHASE.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        refundPurchase(connection);
                    }

                    //Getting the top records
                    else if(selection == Utility.UserCommands.TOP_RECORDS.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        getTopRecords(connection);
                    }

                    //Getting the top labels
                    else if(selection == Utility.UserCommands.TOP_LABELS.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        getTopLabels(connection);
                    }

                    //Getting the users orders
                    else if(selection == Utility.UserCommands.USER_ORDERS.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        getUserOrders(connection);
                    }

                    else if(selection == Utility.UserCommands.QUIT.index) {
                        if (trimmedArgs.size() != 1) {
                            System.err.println(Utility.ERR_INCORRECT_COMMAND);
                            continue;
                        }

                        return;
                    }

                    else
                        System.err.println(Utility.ERR_INCORRECT_COMMAND);
                }
            }
        }

        if(connection != null){
            try {
                connection.close();
            }
            catch (NullPointerException e) {
                System.err.println(Utility.ERR_CANNOT_CLOSE_CONNECTION);
            }
            catch (SQLException e) {
                System.err.println(Utility.ERR_SQL);
            }
        }
    }

    public static void createCatalog(Connection connection, String name, String type){
        try{
            String lastArgument = "?)";
            Object[] params = new Object[]{name, type};

            if(type.equals("DEFAULT")){
                lastArgument = "DEFAULT)";
                params = new Object[]{name};
            }

            Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_CATALOG + lastArgument, params);
            System.out.println(Utility.MSG_CATALOG_ADD_SUCCESS);
            Query.commit(connection);
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void establishLabel(Connection connection, String name, java.util.Date establishment_date){
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_LABELS, null);

            if(set.next())
                Query.printAllRows(set, 5, Utility.MSG_ID_EXAMPLES);

            System.out.println(Utility.NOTICE_LABEL_ID_EXAMPLE);
            
            String id = Query.idInput(in, connection, Utility.MSG_INPUT_LABEL_ID, "Label");

            if(id.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_LABEL, new Object[]{id, name, establishment_date});
            System.out.println(Utility.MSG_LABEL_ESTABLISH_SUCCESS);
            Query.commit(connection);
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    @SuppressWarnings("unchecked")
    public static void prepareRecord(Connection connection, String title, Time length, Date preorder_date, Date release_date, String genre, float price){
        try{
            //selecting the label id
            ResultSet labelSet = Query.executeQuery(connection, SQLStatement.SELECT_LABELS, null);
   
            if(!labelSet.next()){
                Query.rollback(connection);
                System.out.println(Utility.MSG_NO_LABELS_CREATED);
                return;
            }

            String labelID = Query.idSelection(in, labelSet, Utility.MSG_SELECT_LABEL_ID);

            if(labelID.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            //User has to input whether he/she would like to create a new catalog or to select an existing one.
            String ans = Menu.inputYesNo(in, Utility.MSG_CATALOG_SELECTION);

            if(ans.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            int catalogNo;

            if(ans.equals("yes")){
                //creating a new catalog
                while(true){
                    System.out.println(Utility.MSG_CATALOG_CREATION);

                    try{
                        Object[] params = Menu.inputArguments(in, false);

                        if(params == null)
                            return;

                        ArrayList<String> trimmedArgs = (ArrayList<String>)params[0];
                        String name = trimmedArgs.get(0);
                        String type = trimmedArgs.size() == 2 ? trimmedArgs.get(1) : "DEFAULT";

                        createCatalog(connection, name, type);
                        ResultSet catalogSet = Query.executeQuery(connection, SQLStatement.SELECT_CATALOGS, null);
                        catalogSet.last();
                        catalogNo = catalogSet.getInt("No");
                    }
                    catch(NumberFormatException e){
                        System.err.println(Utility.ERR_INCORRECT_COMMAND);
                        continue;
                    }

                    break;
                }
            }
            else {
                //selecting the catalog no
                ResultSet catalogSet = Query.executeQuery(connection, SQLStatement.SELECT_LABEL_CATALOGS, new Object[]{labelID});
                
                if(!catalogSet.next()){
                    Query.rollback(connection);
                    System.out.println(Utility.MSG_NO_CATALOGS_CREATED);
                    return;
                }
                
                String tempNo = Query.idSelection(in, catalogSet, Utility.MSG_SELECT_CATALOG_NO);

                if(tempNo.equals(Utility.CANCEL_STR)){
                    Query.rollback(connection);
                    return;
                }

                catalogNo = Integer.parseInt(tempNo);
            }

            //Inputting the record id
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_LABEL_RECORDS, new Object[]{labelID});

            if(set.next())
                Query.printAllRows(set, -1, Utility.MSG_ID_EXAMPLES);

            System.out.println(Utility.NOTICE_RECORD_ID_EXAMPLE);

            String id = Query.idInput(in, connection, Utility.MSG_INPUT_RECORD_ID, "Record");

            if(id.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            //Inputting artist count
            String artistStr;
            int artistCount;

            while(true){
                System.out.print(Utility.MSG_INPUT_ARTIST_COUNT);
                artistStr = in.nextLine();

                if(artistStr.toLowerCase() == Utility.CANCEL_STR){
                    Query.rollback(connection);
                    return;
                }

                try{
                    artistCount = Integer.parseInt(artistStr);
                }
                catch(NumberFormatException e){
                    System.err.println(Utility.ERR_WRONG_INPUT);
                    continue;
                }

                break;
            }

            //Inputting artists
            ResultSet artistSet = Query.executeQuery(connection, SQLStatement.SELECT_ARTISTS, null);

            if(!artistSet.next()){
                System.out.println(Utility.MSG_NO_ARTISTS_FOUND);
                Query.rollback(connection);
                return;
            }

            ArrayList<String> artists = new ArrayList<>();

            for(int i = 0; i < artistCount; ++i){
                artistStr = Query.idSelection(in, artistSet, Utility.MSG_SELECT_ARTIST_ID);

                if(artistStr.equals(Utility.CANCEL_STR)){
                    Query.rollback(connection);
                    return;
                }

                if(artists.contains(artistStr)){
                    System.out.println(Utility.MSG_ARTIST_PRESENT);
                    --i;
                    continue;
                }

                artists.add(artistStr);
            }

            //Finally, updating the table
            Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_RECORD, new Object[]{id, title, catalogNo, labelID, length, preorder_date, release_date, genre, price});

            for(String artist : artists)
                Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_RECORD_ARTIST, new Object[]{id, artist});

            System.out.println(Utility.MSG_RECORD_PREPARE_SUCCESS);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void registerArtist(Connection connection, String pseudonym, String first_name, String second_name, Date birth_date){
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_ARTISTS, null);

            if(set.next())
                Query.printAllRows(set, 5, Utility.MSG_ID_EXAMPLES);

            System.out.println(Utility.NOTICE_ARTIST_ID_EXAMPLE);

            String id = Query.idInput(in, connection, Utility.MSG_INPUT_ARTIST_ID, "Artist");

            if(id.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_ARTIST, new Object[]{id, pseudonym, first_name, second_name, birth_date});
            System.out.println(Utility.MSG_ARTIST_REGISTER_SUCCESS);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void searchRecordsByTitle(Connection connection, String title){
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_RECORDS_LIKE, new Object[]{"%" + title.toLowerCase() + "%"});
            
            if(!set.next()){
                System.out.println(Utility.MSG_NO_RECORDS_FOUND);
                Query.rollback(connection);
                return;
            }
            
            Query.printAllRows(set, -1, Utility.MSG_RECORDS_FOUND);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void searchArtistsByPseudonym(Connection connection, String pseudonym){
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_ARTISTS_LIKE, new Object[]{"%" + pseudonym.toLowerCase() + "%"});
            
            if(!set.next()){
                System.out.println(Utility.MSG_NO_ARTISTS_FOUND);
                Query.rollback(connection);
                return;
            }

            Query.printAllRows(set, -1, Utility.MSG_ARTISTS_FOUND);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void preorderRecord(Connection connection) {
        try{
            java.sql.Date now = java.sql.Date.valueOf(LocalDateTime.now().toLocalDate());
            ResultSet notReleasedRecords = Query.executeQuery(connection, SQLStatement.SELECT_RECORDS_FOR_PREORDER, new Object[]{localUsername});

            if(!notReleasedRecords.next()){
                System.out.println(Utility.MSG_NO_RECORDS_TO_PREORDER);
                Query.rollback(connection);
                return;
            }

            String id = Query.idSelection(in, notReleasedRecords, Utility.MSG_SELECT_ID_PREORDER);

            if(id.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_ORDER_PREORDER, new Object[]{id, localUsername, now});
            System.out.println(Utility.MSG_PREORDER_SUCCESS);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void cancelPreorder(Connection connection) {
        try{
            ResultSet preorders = Query.executeQuery(connection, SQLStatement.SELECT_PREORDERS, new Object[]{localUsername});

            if(!preorders.next()){
                Query.rollback(connection);
                System.out.println(Utility.MSG_NO_PREORDERS_TO_CANCEL);
                return;
            }

            int no;

            while(true){
                String id = Query.idSelection(in, preorders, Utility.MSG_SELECT_CANCEL_PREORDER_NO);

                if(id.equals(Utility.CANCEL_STR)){
                    Query.rollback(connection);
                    return;
                }

                try{
                    no = Integer.parseInt(id);
                }
                catch(NumberFormatException e){
                    System.err.println(Utility.ERR_WRONG_INPUT);
                    continue;
                }

                break;
            }

            Query.executeUpdateDelete(connection, SQLStatement.DELETE_ORDER, new Object[]{no});
            System.out.println(Utility.MSG_CANCEL_PREORDER_SUCCESS);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void purchaseRecord(Connection connection) {
        try{
            java.sql.Date now = java.sql.Date.valueOf(LocalDateTime.now().toLocalDate());
            ResultSet releasedRecords = Query.executeQuery(connection, SQLStatement.SELECT_RECORDS_FOR_PURCHASE, new Object[]{localUsername});

            if(!releasedRecords.next()){
                System.out.println(Utility.MSG_NO_RECORDS_TO_PURCHASE);
                Query.rollback(connection);
                return;
            }
        
            String id = Query.idSelection(in, releasedRecords, Utility.MSG_SELECT_ID_PURCHASE);

            if(id.equals(Utility.CANCEL_STR)){
                Query.rollback(connection);
                return;
            }

            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_USER_RECORD_ORDERS, new Object[]{id, localUsername});

            //Checking if the record is not preordered (else - it is preordered)
            if(!set.next())
                Query.executeUpdateDelete(connection, SQLStatement.INSERT_INTO_ORDER_PURCHASE, new Object[]{id, localUsername, now});
            else
                Query.executeUpdateDelete(connection, SQLStatement.UPDATE_ORDER_PURCHASE, new Object[]{now, id, localUsername});

            System.out.println(Utility.MSG_PURCHASE_SUCCESS);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void refundPurchase(Connection connection) {
        try{
            ResultSet purchases = Query.executeQuery(connection, SQLStatement.SELECT_PURCHASES, new Object[]{localUsername});

            if(!purchases.next()){
                System.out.println(Utility.MSG_NO_PURCHASES_TO_REFUND);
                Query.rollback(connection);
                return;
            }

            int no;

            while(true){
                String id = Query.idSelection(in, purchases, Utility.MSG_SELECT_REFUND_PURCHASE_NO);

                if(id.equals(Utility.CANCEL_STR)){
                    Query.rollback(connection);
                    return;
                }

                try{
                    no = Integer.parseInt(id);
                }
                catch(NumberFormatException e){
                    System.err.println(Utility.ERR_WRONG_INPUT);
                    continue;
                }

                break;
            }

            Query.executeUpdateDelete(connection, SQLStatement.DELETE_ORDER, new Object[]{no});
            System.out.println(Utility.MSG_REFUND_PURCHASE_SUCCESS);
            Query.commit(connection);
        }
        catch (SQLException e) {
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void getTopRecords(Connection connection) {
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_TOP_RECORDS, null);
       
            if(!set.next()){
                System.out.println(Utility.MSG_NO_RECORDS_TO_DISPLAY);
                Query.rollback(connection);
                return;
            }

            Query.printAllRows(set, 10, Utility.MSG_TOP_10_RECORDS);
            Query.commit(connection);
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void getTopLabels(Connection connection) {
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_TOP_LABELS, null);

            if(!set.next()){
                connection.rollback();
                System.out.println(Utility.MSG_NO_LABELS_TO_DISPLAY);
                return;
            }

            Query.printAllRows(set, 10, Utility.MSG_TOP_10_LABELS);
            Query.commit(connection);
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }

    public static void getUserOrders(Connection connection) {
        try{
            ResultSet set = Query.executeQuery(connection, SQLStatement.SELECT_USER_ORDERS, new Object[]{localUsername});
            
            if(!set.next()){
                System.out.println(Utility.MSG_NO_ORDERS_TO_DISPLAY);
                Query.rollback(connection);
                return;
            }
            
            Query.printAllRows(set, -1, Utility.MSG_USER_ORDERS + localUsername);
            Query.commit(connection);
        }
        catch(SQLException e){
            System.err.println(Utility.ERR_SQL);
            e.printStackTrace();
            Query.rollback(connection);
        }
    }
}
