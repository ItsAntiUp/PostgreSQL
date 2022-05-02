import java.sql.*;
import java.util.*;

public final class SQLStatement{
    /**
    Class, dedicated to SQL statements
    */

    private SQLStatement(){}

    //INSERTS
    public static final String INSERT_INTO_LABEL =          "INSERT INTO kora7392.Label VALUES(?, ?, ?)";
    public static final String INSERT_INTO_RECORD =         "INSERT INTO kora7392.Record VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_CATALOG =        "INSERT INTO kora7392.Catalog VALUES(DEFAULT, ?, ";
    public static final String INSERT_INTO_RECORD_ARTIST =  "INSERT INTO kora7392.RecordArtist VALUES(?, ?)";
    public static final String INSERT_INTO_ARTIST =         "INSERT INTO kora7392.Artist VALUES(?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_ORDER_PREORDER = "INSERT INTO kora7392.Order VALUES(DEFAULT, ?, ?, ?, NULL)";
    public static final String INSERT_INTO_ORDER_PURCHASE = "INSERT INTO kora7392.Order VALUES(DEFAULT, ?, ?, NULL, ?)";

    //UPDATES
    public static final String UPDATE_ORDER_PURCHASE = 
        " UPDATE"                  + 
            " kora7392.Order"      + 
        " SET"                     + 
            " Purchase_date = ?"   + 
        " WHERE"                   + 
            " Record = ? AND"      + 
            " Buyer = ?";

    //DELETES
    public static final String DELETE_ORDER =               "DELETE FROM kora7392.Order O WHERE O.no = ?";

    //REFRESHES
    public static final String REFRESH_LABELS =             "REFRESH MATERIALIZED VIEW kora7392.Label_purchases";

    //SELECTS
    public static final String SELECT_LABELS =              "SELECT id AS ID, name AS Name FROM kora7392.Label ORDER BY ID ASC";
    public static final String SELECT_RECORDS =             "SELECT id AS ID, title AS Title FROM kora7392.Record ORDER BY ID ASC";
    public static final String SELECT_CATALOGS =            "SELECT no AS No, name AS Name FROM kora7392.Catalog ORDER BY No ASC";
    public static final String SELECT_ARTISTS =             "SELECT id AS ID, pseudonym AS Pseudonym FROM kora7392.Artist ORDER BY ID ASC";

    public static final String SELECT_ARTISTS_LIKE =        "SELECT id AS ID, pseudonym AS Pseudonym FROM kora7392.Artist WHERE LOWER(pseudonym) LIKE ? ORDER BY ID ASC";

    public static final String SELECT_RECORDS_LIKE =        
        " SELECT"                                           + 
            " R.id AS ID,"                                  + 
            " R.title AS Title,"                            + 
            " string_agg(A.pseudonym, ', ') AS Artists"     +
        " FROM"                                             + 
            " kora7392.Record R"                            +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            + 
            " LOWER(R.title) LIKE ? "                       +
        " GROUP BY"                                         +
            " R.id,"                                        +
            " R.title"                                      +
        " ORDER BY "                                        + 
            " R.id ASC";

    public static final String SELECT_USER_ORDERS =         
        " SELECT"                                           +
            " O.no AS No,"                                  +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists,"    +
            " O.preorder_date AS Preordered,"               +
            " O.purchase_date AS Purchased"                 +
        " FROM"                                             +
            " kora7392.Order O"                             +
        " INNER JOIN"                                       +
            " kora7392.Record R"                            +
        " ON"                                               +
            " R.id = O.record"                              +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " O.Buyer = ?"                                  +
        " GROUP BY"                                         +
            " O.no,"                                        +
            " R.title,"                                     +
            " O.preorder_date,"                             +
            " O.purchase_date"                              +
        " ORDER BY"                                         +
            " O.no ASC";

    public static final String SELECT_USER_RECORD_ORDERS =  
        " SELECT"                                           +
            " O.no AS No,"                                  +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists,"    +
            " O.preorder_date AS Preordered,"               +
            " O.purchase_date AS Purchased"                 +
        " FROM"                                             +
            " kora7392.Order O"                             +
        " INNER JOIN"                                       +
            " kora7392.Record R"                            +
        " ON"                                               +
            " R.id = O.record"                              +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " O.Record = ? AND"                             +
            " O.Buyer = ?"                                  +
        " GROUP BY"                                         +
            " O.no,"                                        +
            " R.title,"                                     +
            " O.preorder_date,"                             +
            " O.purchase_date"                              +
        " ORDER BY"                                         +
            " O.no ASC";

    public static final String SELECT_RECORDS_FOR_PREORDER =   
        " SELECT"                                           +
            " R.id AS ID,"                                  +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists,"    +
            " R.label AS Label,"                            +
            " R.price AS Price"                             +
        " FROM"                                             +
            " kora7392.Record R"                            + 
        " INNER JOIN"                                       +
            " kora7392.Record_releases RR"                  + 
        " ON"                                               +
            " R.id = RR.id"                                 + 
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " RR.is_released = FALSE AND"                   + 
            " R.id NOT IN ("                                +
                " SELECT"                                   +
                    " O.Record"                             + 
                " FROM"                                     +
                    " kora7392.Order O"                     + 
                " WHERE"                                    +
                    " O.Buyer = ?"                          +
            ")"                                             +
        " GROUP BY"                                         +
            " R.id,"                                        +
            " R.title,"                                     +
            " R.label"                                      +
        " ORDER BY"                                         +
            " R.id ASC";

    public static final String SELECT_RECORDS_FOR_PURCHASE =      
        " SELECT"                                           +
            " R.id AS ID,"                                  +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists,"    +
            " R.label AS Label,"                            +
            " R.price AS Price"                             +
        " FROM"                                             +
            " kora7392.Record R"                            +
        " INNER JOIN"                                       +
            " kora7392.Record_releases RR"                  +
        " ON"                                               +
            " R.id = RR.id"                                 +
        " INNER JOIN"                                       +
            " kora7392.Record_purchases RP"                 +
        " ON"                                               +
            " R.id = RP.id"                                 +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " RR.is_released = TRUE AND"                    +
            " R.id NOT IN ("                                +
                " SELECT"                                   +
                    " O.Record"                             +
                " FROM"                                     +
                    " kora7392.Order O"                     +
                " WHERE"                                    +
                    " O.Buyer = ? AND"                      +
                    " O.Purchase_date IS NOT NULL"          +
            ")"                                             +
        " GROUP BY"                                         +
            " R.id,"                                        +
            " R.title,"                                     +
            " R.label"                                      +
        " ORDER BY"                                         +
            " R.id ASC";

    public static final String SELECT_TOP_RECORDS =        
        " SELECT"                                           +
            " R.id AS ID,"                                  +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists,"    +
            " RP.copies_bought AS Bought"                   +
        " FROM"                                             +
            " kora7392.Record R"                            +
        " INNER JOIN"                                       +
            " kora7392.Record_purchases RP"                 +
        " ON"                                               +
            " R.id = RP.id"                                 +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " GROUP BY"                                         +
            " R.id,"                                        +
            " R.title,"                                     +
            " RP.copies_bought"                             +
        " ORDER BY"                                         +
            " copies_bought DESC";

    public static final String SELECT_TOP_LABELS =
        " SELECT"                               +
            " label AS Label,"                  +
            " name AS Name,"                    +
            " copies_bought AS Bought"          +
        " FROM"                                 +
            " kora7392.Label_purchases LP"      +
        " ORDER BY"                             +
            " LP.copies_bought DESC";

    public static final String SELECT_LABEL_CATALOGS =            
        " SELECT DISTINCT"           +
            " C.No AS No,"           +
            " C.name AS Name"       +
        " FROM"                      +
            " kora7392.Catalog C"    +
        " INNER JOIN"                +
            " kora7392.Record R"     +
        " ON"                        +
            " R.Catalog = C.No"      +
        " INNER JOIN"                +
            " kora7392.Label L"      +
        " ON"                        +
            " L.id = R.Label"        +
        " WHERE"                     +
            " L.id = ?"              +
        " ORDER BY"                  +
            " C.No ASC";

    public static final String SELECT_LABEL_RECORDS =            
        " SELECT DISTINCT"                                  +
            " R.id AS ID,"                                  +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists,"    +
            " R.price AS Price"                             +
        " FROM"                                             +              
            " kora7392.Record R"                            +
        " INNER JOIN"                                       +
            " kora7392.Label L"                             +
        " ON"                                               +
            " L.id = R.Label"                               +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " L.id = ?"                                     +
        " GROUP BY"                                         +
            " R.id,"                                        +
            " R.title,"                                     +
            " R.price"                                      +
        " ORDER BY"                                         +
            " R.id ASC";

    public static final String SELECT_PREORDERS =            
        " SELECT"                                           +
            " O.no AS Order_no,"                            +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists"     +
        " FROM"                                             +
            " kora7392.Order O"                             +
        " INNER JOIN"                                       +
            " kora7392.Record R"                            +
        " ON"                                               +
            " R.id = O.record"                              +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " O.Buyer = ? AND"                              +
            " O.purchase_date IS NULL"                      +
        " GROUP BY"                                         +
            " O.no,"                                        +
            " R.title"                                      +
        " ORDER BY"                                         +
            " O.no ASC";            

    public static final String SELECT_PURCHASES =            
        " SELECT"                                           +
            " O.no AS Order_no,"                            +
            " R.title AS Title,"                            +
            " string_agg(A.pseudonym, ', ') AS Artists"     +
        " FROM"                                             +
            " kora7392.Order O"                             +
        " INNER JOIN"                                       +
            " kora7392.Record R"                            +
        " ON"                                               +
            " R.id = O.record"                              +
        " INNER JOIN"                                       +
            " kora7392.RecordArtist RA"                     +
        " ON"                                               +
            " R.id = RA.Record"                             +
        " INNER JOIN"                                       +
            " kora7392.Artist A"                            +
        " ON"                                               +
            " RA.Artist = A.id"                             +
        " WHERE"                                            +
            " O.Buyer = ? AND"                              +
            " O.purchase_date IS NOT NULL"                  +
        " GROUP BY"                                         +
            " O.no,"                                        +
            " R.title"                                      +      
        " ORDER BY"                                         +
            " O.no ASC";                                   
}