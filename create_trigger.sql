---------------TRIGGER FUNCTIONS---------------

CREATE OR REPLACE FUNCTION CheckArtistCount()
  RETURNS trigger AS $Artist_count$
BEGIN
	IF (1 IN (
            SELECT 
                Num 
            FROM (
                SELECT
                    RA.Record,
                    COUNT(RA.Artist) AS Num
                FROM
                    kora7392.RecordArtist RA
                WHERE
                    OLD.ID IN (
                        SELECT
                            RR.Artist
                        FROM
                            kora7392.RecordArtist RR
                        WHERE
                            RR.Record = RA.Record
                    )
                GROUP BY 
                    RA.Record
            ) AS Query
        )
    )
    THEN
		RAISE EXCEPTION 'Artist % cannot be deleted - it is the only artist on some records.', OLD.pseudonym;
	END IF;
	RETURN NEW;
END; $Artist_count$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION CheckArtistBirthDate()
  RETURNS trigger AS $Artist_birth_date$
BEGIN
	IF (NEW.Birth_date > CURRENT_DATE - INTERVAL '16 YEARS')
    THEN
		RAISE EXCEPTION 'Artist % is too young!', NEW.pseudonym;
	END IF;
	RETURN NEW;
END; $Artist_birth_date$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION CheckRecordID()
  RETURNS trigger AS $Record_ID$
BEGIN
	IF (NEW.ID NOT LIKE NEW.Label || '%' OR 
        TRUE NOT IN (SELECT RIGHT(NEW.ID, 4) ~ '^\d+$') OR 
        CAST(COALESCE(RIGHT(NEW.ID, 4),'0') AS INTEGER) = 0) 
    THEN
		RAISE EXCEPTION 'Record ID % is formed incorrectly. (First 6 symbols = label ID, last 4 = a number starting from 1)', NEW.ID;
	END IF;
	RETURN NEW;
END; $Record_ID$
LANGUAGE 'plpgsql';

---------------ACTUAL TRIGGERS---------------

CREATE TRIGGER Artist_count
    BEFORE 
        DELETE
    ON 
        kora7392.Artist
    FOR EACH ROW
        EXECUTE FUNCTION CheckArtistCount();

CREATE TRIGGER Artist_birth_date
    BEFORE 
        INSERT OR UPDATE 
    ON 
        kora7392.Artist
    FOR EACH ROW
        EXECUTE FUNCTION CheckArtistBirthDate();

CREATE TRIGGER Record_ID
    BEFORE 
        INSERT OR UPDATE 
    ON 
        kora7392.Record
    FOR EACH ROW
        EXECUTE FUNCTION CheckRecordID();