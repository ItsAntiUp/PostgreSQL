import java.sql.*;

public final class Utility{
    /***
    Class, dedicated to auxiliary methods and constants.
    */

    public static final String ERR_INCORRECT_COMMAND =          "Incorrect command!";
    public static final String ERR_CANNOT_CLOSE_CONNECTION =    "Connection cannot be closed!";
    public static final String ERR_CLASS_NOT_FOUND =            "Driver class not found!";
    public static final String ERR_CANNOT_CONNECT =             "Failed to connect to database!";
    public static final String ERR_SQL =                        "SQL error!";
    public static final String ERR_UNEXPECTED_SQL =             "Unexpected SQL error!";
    public static final String ERR_UNEXPECTED =                 "Unexpected error!";
    public static final String ERR_SYMBOL_NOT_ALLOWED =         "Symbol not allowed - \"";
    public static final String ERR_ID_PRESENT =                 "This id is already present!";

    public static final String ERR_WRONG_DATE =                 "Wrong date!";
    public static final String ERR_WRONG_CATALOG_NO =           "Wrong catalog number!";
    public static final String ERR_WRONG_RECORD_DATES =         "Wrong preorder/release dates!";
    public static final String ERR_WRONG_LENGTH =               "Wrong length!";
    public static final String ERR_WRONG_PRICE =                "Wrong price!";
    public static final String ERR_WRONG_INPUT =                "Wrong input!";

    public static final String NOTICE_RECORD_ID_EXAMPLE =       "NOTICE - The first 6 ID symbols should be identical to the label ID, and the last 4 should be a number from 0001 to 9999.";
    public static final String NOTICE_LABEL_ID_EXAMPLE =        "NOTICE - The ID should contain exactly 6 symbols.";
    public static final String NOTICE_ARTIST_ID_EXAMPLE =       "NOTICE - The ID should contain exactly 10 symbols. Ideally - first 4 are letters, related to artist's name, last 6 are numbers, related to the artist's birth date.";

    public static final String MSG_CONNECTED_SUCCESS =          "Successfully connected to the database!";
    public static final String MSG_NO_RECORDS_TO_PREORDER =     "No records available to preorder.";
    public static final String MSG_NO_PREORDERS_TO_CANCEL =     "No preorders available to cancel.";
    public static final String MSG_NO_RECORDS_TO_PURCHASE =     "No records available to purchase.";
    public static final String MSG_NO_PURCHASES_TO_REFUND =     "No purchases available to refund.";
    public static final String MSG_NO_RECORDS_FOUND =           "No records found.";
    public static final String MSG_NO_ARTISTS_FOUND =           "No artists found.";
    public static final String MSG_NO_RECORDS_TO_DISPLAY =      "No records to display.";
    public static final String MSG_NO_LABELS_TO_DISPLAY =       "No labels to display.";
    public static final String MSG_NO_ORDERS_TO_DISPLAY =       "No orders to display.";
    public static final String MSG_NO_LABELS_CREATED =          "No labels created.";
    public static final String MSG_NO_CATALOGS_CREATED =        "No catalogs created.";
    public static final String MSG_SELECT_ID_PREORDER =         "Please select the number of the record you want to preorder: ";
    public static final String MSG_SELECT_ID_PURCHASE =         "Please select the number of the record you want to purchase: ";
    public static final String MSG_INPUT_ARTIST_COUNT =         "Please input the artist count: ";
    public static final String MSG_INPUT_ARTIST_ID =            "Please input the artist id: ";
    public static final String MSG_INPUT_LABEL_ID =             "Please input the label id: ";
    public static final String MSG_INPUT_RECORD_ID =            "Please input the record id: ";
    public static final String MSG_SELECT_ARTIST_ID =           "Please select the artist number: ";
    public static final String MSG_SELECT_LABEL_ID =            "Please select the label number: ";
    public static final String MSG_SELECT_CATALOG_NO =          "Please select the catalog number: ";
    public static final String MSG_SELECT_ARTIST_NO =           "Please select the artist number: ";
    public static final String MSG_SELECT_CANCEL_PREORDER_NO =  "Please select the number of the order to cancel the preorder: ";
    public static final String MSG_SELECT_REFUND_PURCHASE_NO =  "Please select the number of the order refund the purchase: ";
    public static final String MSG_PREORDER_SUCCESS =           "Record preordered successfully!";
    public static final String MSG_CANCEL_PREORDER_SUCCESS =    "Preorder cancelled successfully!";
    public static final String MSG_PURCHASE_SUCCESS =           "Record purchased successfully!";
    public static final String MSG_REFUND_PURCHASE_SUCCESS =    "Purchase refunded successfully!";
    public static final String MSG_CATALOG_ADD_SUCCESS =        "Catalog added successfully!";
    public static final String MSG_LABEL_ESTABLISH_SUCCESS =    "Label established successfully!";
    public static final String MSG_RECORD_PREPARE_SUCCESS =     "Record prepared successfully!";
    public static final String MSG_ARTIST_REGISTER_SUCCESS =    "Artist registered successfully!";
    public static final String MSG_ID_EXAMPLES =                "ID examples: ";
    public static final String MSG_RECORDS_FOUND =              "Records found: ";
    public static final String MSG_ARTISTS_FOUND =              "Artists found: ";
    public static final String MSG_TOP_10_RECORDS =             "Top 10 records: ";
    public static final String MSG_TOP_10_LABELS =              "Top 10 labels: ";
    public static final String MSG_USER_ORDERS =                "Orders for the user: ";
    public static final String MSG_CATALOG_SELECTION =          "Would you like to create a new catalog (yes / no)? : ";
    public static final String MSG_CATALOG_CREATION =           "Please input the catalog info: <name>, <<type>>";
    public static final String MSG_ARTIST_PRESENT =             "This artist is already present!";

    public static final String DRIVER_CLASS_PATH =              "org.postgresql.Driver";
    public static final String CONNECTION_URL =                 "jdbc:postgresql://pgsql2.mif/studentu";

    public static final String USERNAME =                       "kora7392";
    public static final String CANCEL_STR =                     "cancel";

    private Utility(){}

    //Enums for admin and user command codes
    public enum AdminCommands{
        QUIT(0),
        ADD_CATALOG(1),
        ESTABLISH_LABEL(2),
        PREPARE_RECORD(3),
        REGISTER_ARTIST(4);

        public final int index;

        AdminCommands(int index){
            this.index = index;
        }
    }

    public enum UserCommands{
        QUIT(0),
        SEARCH_RECORD_TITLE(1),
        SEARCH_ARTIST_PSEUDONYM(2),
        PREORDER_RECORD(3),
        CANCEL_PREORDER(4),
        PURCHASE_RECORD(5),
        REFUND_PURCHASE(6),
        TOP_RECORDS(7),
        TOP_LABELS(8),
        USER_ORDERS(9);

        public final int index;

        UserCommands(int index){
            this.index = index;
        }
    }

    public static void printWelcomeText(){
        System.out.println("\n*********Welcome To Beatport**********\n");
    }

    public static void printLoginText(){
        System.out.print("Please input your username: ");
    }

    public static void printAdminMenu(){
        System.out.println("\nPlease select a command:");
        System.out.println("[0]                                                                             ---> Quit");
        System.out.println("[1, <name>, <<type>>]                                                           ---> Create a new catalog");
        System.out.println("[2, <name>, <establishment_date>]                                               ---> Establish a new label");
        System.out.println("[3, <title>, <length>, <preorder_date>, <release_date>, <genre>, <price>]       ---> Prepare record for release");
        System.out.println("[4, <pseudonym>, <first_name>, <second_name>, <birth_date>]                     ---> Register a new artist");
        System.out.print("\n");
    }

    public static void printUserMenu(){
        System.out.println("\nPlease select a command:");
        System.out.println("[0]                 ---> Quit");
        System.out.println("[1, <title>]        ---> Search records by title");
        System.out.println("[2, <pseudonym>]    ---> Search artists by pseudonym");
        System.out.println("[3]                 ---> Preorder a record");
        System.out.println("[4]                 ---> Cancel preorder");
        System.out.println("[5]                 ---> Purchase a record");
        System.out.println("[6]                 ---> Refund a purchase");
        System.out.println("[7]                 ---> Record spotlight");
        System.out.println("[8]                 ---> Label spotlight");
        System.out.println("[9]                 ---> My orders");
        System.out.print("\n");
    }
}