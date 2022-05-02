---------------CHECKS---------------

--Constraint : Catalog_name_constraint 
UPDATE kora7392.Catalog SET Name = '' WHERE No = 1;                                     ---> RESTRICTED
UPDATE kora7392.Catalog SET Name = 'BLACK BOOK ID''S: CHAPTER 3' WHERE No = 1;          ---> ALLOWED

--Constraint : Catalog_type_constraint 
UPDATE kora7392.Catalog SET Type = 'LP' WHERE No = 1;                                   ---> RESTRICTED
UPDATE kora7392.Catalog SET Type = 'EP' WHERE No = 1;                                   ---> ALLOWED

--Constraint : Label_ID_constraint 
UPDATE kora7392.Label SET ID = '' WHERE ID = 'BLBORE';                                  ---> RESTRICTED

--Constraint : Label_name_constraint 
UPDATE kora7392.Label SET Name = '' WHERE ID = 'BLBORE';                                ---> RESTRICTED
UPDATE kora7392.Label SET Name = 'Black Book Records' WHERE ID = 'BLBORE';              ---> ALLOWED

--Constraint : Label_establishment_date_constraint 
UPDATE kora7392.Label SET Establishment_date = '1899-01-01' WHERE ID = 'BLBORE';        ---> RESTRICTED
UPDATE kora7392.Label SET Establishment_date = '2030-05-22' WHERE ID = 'BLBORE';        ---> RESTRICTED
UPDATE kora7392.Label SET Establishment_date = '2017-08-25' WHERE ID = 'BLBORE';        ---> ALLOWED

--Constraint : Artist_ID_constraint 
UPDATE kora7392.Artist SET ID = '' WHERE ID = 'CRLA820808';                             ---> RESTRICTED

--Constraint : Artist_pseudonym_constraint
UPDATE kora7392.Artist SET Pseudonym = '' WHERE ID = 'CRLA820808';                      ---> RESTRICTED
UPDATE kora7392.Artist SET Pseudonym = 'Chris Lake' WHERE ID = 'CRLA820808';            ---> ALLOWED

--Constraint : Artist_first_name_constraint 
UPDATE kora7392.Artist SET First_name = '' WHERE ID = 'CRLA820808';                     ---> RESTRICTED
UPDATE kora7392.Artist SET First_name = 'Chris' WHERE ID = 'CRLA820808';                ---> ALLOWED

--Constraint : Artist_last_name_constraint 
UPDATE kora7392.Artist SET Last_name = '' WHERE ID = 'CRLA820808';                      ---> RESTRICTED
UPDATE kora7392.Artist SET Last_name = 'Lake' WHERE ID = 'CRLA820808';                  ---> ALLOWED

--Constraint : Artist_birth_date_constraint 
UPDATE kora7392.Artist SET Birth_date = '1899-01-01' WHERE ID = 'CRLA820808';           ---> RESTRICTED
UPDATE kora7392.Artist SET Birth_date = '1982-08-08' WHERE ID = 'CRLA820808';           ---> ALLOWED

--Constraint : Record_ID_constraint 
UPDATE kora7392.Record SET ID = '' WHERE ID = 'BLBORE0001';                             ---> RESTRICTED

--Constraint : Record_title_constraint 
UPDATE kora7392.Record SET Title = '' WHERE ID = 'BLBORE0004';                          ---> RESTRICTED
UPDATE kora7392.Record SET Title = 'Snake Charmer' WHERE ID = 'BLBORE0004';             ---> ALLOWED

--Constraint : Record_length_constraint 
UPDATE kora7392.Record SET Length = '00:00:00' WHERE ID = 'BLBORE0001';                 ---> RESTRICTED
UPDATE kora7392.Record SET Length = '00:06:47' WHERE ID = 'BLBORE0001';                 ---> ALLOWED

--Constraint : Record_preorder_date_constraint 
UPDATE kora7392.Record SET Preorder_date = '1899-01-01' WHERE ID = 'BLBORE0001';        ---> RESTRICTED
UPDATE kora7392.Record SET Preorder_date = '2020-01-06' WHERE ID = 'BLBORE0001';        ---> ALLOWED

--Constraint : Record_release_date_constraint 
UPDATE kora7392.Record SET Release_date = '2020-01-06' WHERE ID = 'BLBORE0001';         ---> RESTRICTED
UPDATE kora7392.Record SET Release_date = '2020-01-17' WHERE ID = 'BLBORE0001';         ---> ALLOWED

--Constraint : Record_genre_constraint
UPDATE kora7392.Record SET Genre = 'Jazz' WHERE ID = 'BLBORE0001';                      ---> RESTRICTED
UPDATE kora7392.Record SET Genre = 'House' WHERE ID = 'BLBORE0001';                     ---> ALLOWED

--Constraint : Record_price_constraint
UPDATE kora7392.Record SET Price = -10.99 WHERE ID = 'BLBORE0001';                      ---> RESTRICTED
UPDATE kora7392.Record SET Price = 1.99 WHERE ID = 'BLBORE0001';                        ---> ALLOWED

--Constraint : Order_buyer_constraint
UPDATE kora7392.Order SET Buyer = '' WHERE No = 1;                                      ---> RESTRICTED
UPDATE kora7392.Order SET Buyer = 'user1' WHERE No = 1;                                 ---> ALLOWED

--Constraint : Order_preorder_constraint
UPDATE kora7392.Order SET Preorder_date = NULL WHERE No = 1;                            ---> ALLOWED
UPDATE kora7392.Order SET Preorder_date = '1899-01-01' WHERE No = 1;                    ---> RESTRICTED
UPDATE kora7392.Order SET Preorder_date = '2020-01-06' WHERE No = 1;                    ---> ALLOWED

--Constraint : Order_purchase_constraint
UPDATE kora7392.Order SET Purchase_date = NULL WHERE No = 1;                            ---> ALLOWED
UPDATE kora7392.Order SET Purchase_date = '1899-01-01' WHERE No = 1;                    ---> RESTRICTED
UPDATE kora7392.Order SET Purchase_date = '2020-01-06' WHERE No = 1;                    ---> RESTRICTED
UPDATE kora7392.Order SET Purchase_date = '2020-01-17' WHERE No = 1;                    ---> ALLOWED

--Constraint : Order_dates_constraint
UPDATE kora7392.Order SET Preorder_date = NULL WHERE No = 34;                           ---> RESTRICTED

---------------TRIGGERS---------------

--Trigger : Artist_count
DELETE FROM kora7392.Artist WHERE ID = 'CRLA820808';                                    ---> RESTRICTED
DELETE FROM kora7392.Artist WHERE ID = 'OLHE950201';                                    ---> ALLOWED (since artist does not have any records)
DELETE FROM kora7392.Artist WHERE ID = 'MIHE900516';                                    ---> ALLOWED (not the only artist)

--Trigger : Artist_birth_date
UPDATE kora7392.Artist SET Birth_date = '2015-01-01' WHERE ID = 'CRLA820808';           ---> RESTRICTED

--Trigger : Record_ID
UPDATE kora7392.Record SET ID = '*LBORE0001' WHERE ID = 'BLBORE0001';                   ---> RESTRICTED
UPDATE kora7392.Record SET ID = 'BLBORE000b' WHERE ID = 'BLBORE0001';                   ---> RESTRICTED
UPDATE kora7392.Record SET ID = 'BLBORE0000' WHERE ID = 'BLBORE0001';                   ---> RESTRICTED
UPDATE kora7392.Record SET ID = 'BLBORE0.01' WHERE ID = 'BLBORE0001';                   ---> RESTRICTED
UPDATE kora7392.Record SET ID = 'BLBORE0010' WHERE ID = 'BLBORE0001';                   ---> ALLOWED
UPDATE kora7392.Record SET ID = 'BLBORE0001' WHERE ID = 'BLBORE0010';                   ---> ALLOWED

---------------OTHER---------------

--Constraint : Record_foreign_label
DELETE FROM kora7392.Label WHERE ID = 'BLBORE';                                         ---> RESTRICTED
